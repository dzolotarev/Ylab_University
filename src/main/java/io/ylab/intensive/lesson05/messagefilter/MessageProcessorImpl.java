package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

import static io.ylab.intensive.lesson05.messagefilter.QueueParams.*;


/**
 * @author Denis Zolotarev
 */
@Component
public class MessageProcessorImpl implements MessageProcessor {
    private ConnectionFactory connectionFactory;
    private SentenceManipulator sentenceManipulator;

    public MessageProcessorImpl(ConnectionFactory connectionFactory, SentenceManipulator sentenceManipulator) {
        this.connectionFactory = connectionFactory;
        this.sentenceManipulator = sentenceManipulator;
    }

    @Override
    public void processInputMessage(Channel channel) throws IOException, SQLException, TimeoutException {
        channel.queueDeclare(INPUT_QUEUE, true, false, false, null);
        GetResponse message = channel.basicGet(INPUT_QUEUE, true);
        if (message != null) {
            String input = new String(message.getBody());
            String output = sentenceManipulator.sentenceProcess(input);
            sendOutputMessage(output);
        }
    }

    @Override
    public void sendOutputMessage(String message) throws IOException, TimeoutException {
        try (Connection connection = connectionFactory.newConnection(); Channel channel = getChannel(connection)) {
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
        }
    }

    @Override
    public void start() throws IOException, TimeoutException, InterruptedException, SQLException {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel();) {
            while (!Thread.currentThread().isInterrupted()) {
                processInputMessage(channel);
                Thread.sleep(100);
            }
        }
    }

    private Channel getChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(OUTPUT_QUEUE, true, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueBind(OUTPUT_QUEUE, EXCHANGE_NAME, ROUTING_KEY);
        return channel;
    }
}
