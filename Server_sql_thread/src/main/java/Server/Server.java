package Server;

import Server.CollectionObjects.Coordinates;
import Server.CollectionObjects.Location_from;
import Server.CollectionObjects.Location_to;
import Server.CollectionObjects.Route;
import Server.Threads.ReaderThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static Selector selector;
    private static ServerSocketChannel serverSocket;

    static final Logger logger = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /*
        logger.debug("This is a debug message");
        logger.info("This is an info message");
        logger.warn("This is a warn message");
        logger.error("This is an error message");
        logger.fatal("This is a fatal message");
        */
        if (args.length < 1) {
            System.out.println("Пожалуйста введите порт в коммандной строке");
            logger.error("Порт не был указан");
            return;
        }

        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (Exception e) {
            return;
        }

        logger.info("Порт " + port);

        //пытаемся загрузить коллекцию с базы
        try {
            DataBase db = new DataBase();

            //заполняем дату создания коллекции
            ResultSet rs = db.executeQuery("SELECT (creation_date) from s288870.\"Info\"");

            if (!rs.next()) {
                int creation_date = db.executeUpdate("INSERT INTO s288870.\"Info\" (creation_date) " +
                        "values ('" + utils.collection.getTime() + "');");
            } else utils.collection.setCreation_date(ZonedDateTime.parse(rs.getString(1)));
            // return "Ошибка выполнения запроса";

            rs = db.executeQuery("SELECT * from s288870.\"Route\"");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Coordinates coordinates = new Coordinates(rs.getInt("x"), rs.getInt("y"));
                ZonedDateTime creationDate = ZonedDateTime.parse(rs.getString("creation_date"));
                Location_from from = new Location_from(rs.getDouble("x_from"), rs.getLong("y_from"), rs.getString("name_from"));
                Location_to to = new Location_to(rs.getLong("x_to"), rs.getInt("y_to"), rs.getInt("z_to"), rs.getString("name_to"));
                String owner = rs.getString("owner");
                Route temp = new Route(id, name, coordinates, creationDate, from, to, owner);
                utils.collection.getSet().add(temp);
            }
            logger.info("Коллекция загружена успешно");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Ошибка подключения к базе данных");
            System.exit(0);
        }

        ConsoleReader cn = new ConsoleReader(utils.collection);
        cn.start();

        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.configureBlocking(false);
        serverSocket.bind(new InetSocketAddress(port));
        logger.info("Сервер запущен");


        while (true) {
            try {
                logger.info("Ожидаем соединения");
                Selector selector = accept();
                logger.info("Соединение принято, считываем команду");

                SocketChannel channel = null;
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                for (SelectionKey key : selectedKeys) {
                    if (key.isReadable()) {
                        utils.cachedThreadPool.execute(new ReaderThread((SocketChannel) key.channel(),selector));
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static Selector accept() throws IOException {
        selector = Selector.open();
        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            selector.select();
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            for (SelectionKey key : selectedKeys) {
                if (key.isAcceptable()) {
                    key.cancel();
                    SocketChannel client = serverSocket.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ);
                    return selector;
                }
            }
        }
    }
}