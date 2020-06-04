package Server.Threads;

import Server.Answer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class SendThread implements Runnable {

    static final Logger logger = LogManager.getRootLogger();

    Answer answer;
    ByteBuffer buffer;
    SocketChannel channel;
    Selector selector;

    public SendThread(SocketChannel channel, Selector selector, Answer answer, ByteBuffer buffer) {
        this.answer=answer;
        this.buffer=buffer;
        this.channel=channel;
        this.selector=selector;
    }

    @Override
    public void run() {
        try {
            buffer.clear();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(answer);
            out.flush();

            byte[] bytes = bos.toByteArray();
            buffer = ByteBuffer.wrap(bytes);

            channel = null;
            while (channel == null) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                for (SelectionKey key : selectedKeys) {
                    if (key.isWritable()) {
                        channel = (SocketChannel) key.channel();
                        while (buffer.hasRemaining()) {
                            channel.write(buffer);
                        }
                        break;
                    }
                }
            }

            try {
                Thread.sleep(100);
            } catch (Exception e) {
                e.printStackTrace();
            }

            channel.socket().close();
            channel.close();
            selector.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
}
