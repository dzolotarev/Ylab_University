package io.ylab.intensive.lesson05.eventsourcing.db;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.ylab.intensive.lesson05.eventsourcing.QueueProps.QUEUE_NAME;


/**
 * @author Denis Zolotarev
 */
@Component
public class MessageProcessorImpl implements MessageProcessor {
    private static final String SAVE = "save";
    private static final String DELETE = "delete";

    private DbClient dbClient;
    private ConnectionFactory connectionFactory;
    private ObjectMapper objectMapper;

    public MessageProcessorImpl(DbClient dbClient, ConnectionFactory connectionFactory) {
        this.dbClient = dbClient;
        this.connectionFactory = connectionFactory;
        objectMapper = new ObjectMapper();
    }

    @Override
    public void start() throws Exception {
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel();) {
            while (!Thread.currentThread().isInterrupted()) {
                processSingleMessage(channel);
                Thread.sleep(100);
            }
        }

    }

    @Override
    public void processSingleMessage(Channel channel) throws Exception {
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        GetResponse message = channel.basicGet(QUEUE_NAME, true);
        if (message != null) {
            String received = new String(message.getBody());
            Map<String, Person> commandMap = unpack(received);
            for (Map.Entry<String, Person> command : commandMap.entrySet()) {
                switch (command.getKey()) {
                    case SAVE: {
                        dbClient.save(command.getValue());
                        break;
                    }
                    case DELETE: {
                        dbClient.delete(command.getValue().getId());
                        break;
                    }
                    default: {
                        System.err.println("Unknown command");
                        break;
                    }
                }
            }
        }
    }

    private Map<String, Person> unpack(String message) throws JsonProcessingException {
        TypeReference<Map<String, Person>> mapType = new TypeReference<>() {
        };
        return objectMapper.readValue(message, mapType);
    }
}
