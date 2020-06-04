package Server.Threads;

import Server.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class ReaderThread implements Runnable {

    static final Logger logger = LogManager.getRootLogger();

    SocketChannel channel;
    Selector selector;
    RouteCollection collection;

    public ReaderThread(SocketChannel channel, Selector selector) {
        this.channel = channel;
        this.selector = selector;
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(16384);
        try {
            channel.read(buffer);
            buffer.flip();
            channel.register(selector, SelectionKey.OP_WRITE);

            //Чтение размера строки с командой
            byte[] bytes = new byte[buffer.limit()];//buffer.array();
            buffer.get(bytes);

            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));

            Container container = new Container("empty");

            try {
                container = (Container) inputStream.readObject();
            } catch (EOFException eofex) {
                //ничего не делаем
            }

            if (!container.getCommand().equalsIgnoreCase("empty")) {
                System.out.println("Считано " + bytes.length + " байт");

                utils.forkJoinPool.execute(new DispatchThread(channel, selector, container, buffer));
            }

        } catch (Exception e) {
            logger.error("Ошибка чтения запроса");
            e.printStackTrace();
        }
    }
}
