package Server.Threads;

import Server.Answer;
import Server.Container;
import Server.Dispatcher;
import Server.utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class DispatchThread implements Runnable {

    static final Logger logger = LogManager.getRootLogger();

    Container container;
    ByteBuffer buffer;
    SocketChannel channel;
    Selector selector;

    public DispatchThread(SocketChannel channel, Selector selector, Container container, ByteBuffer buffer) {
        this.container=container;
        this.buffer=buffer;
        this.channel=channel;
        this.selector=selector;
    }

    @Override
    public void run() {
        try {
            System.out.println("Команда: " + container.getCommand());
            Answer answer = new Answer(Dispatcher.parseCommand(container));// запускаем статичн
            utils.fixedThreadPool.execute(new SendThread(channel, selector, answer, buffer));
        } catch (Exception e) {
            logger.error("Ошибка обработки запроса");
            e.printStackTrace();
        }
    }
}
