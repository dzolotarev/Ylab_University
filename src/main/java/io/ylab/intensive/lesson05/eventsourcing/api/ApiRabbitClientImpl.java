package io.ylab.intensive.lesson05.eventsourcing.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import static io.ylab.intensive.lesson05.eventsourcing.QueueProps.*;

/**
 * @author Denis Zolotarev
 */
@Component
public class ApiRabbitClientImpl implements ApiRabbitClient {
    private static final String SAVE = "save";
    private static final String DELETE = "delete";
    private ConnectionFactory connectionFactory;
    private ObjectMapper objectMapper;

    public ApiRabbitClientImpl(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        objectMapper = new ObjectMapper();
    }

    private void sendMessage(String json) throws IOException, TimeoutException {
        try (Connection connection = connectionFactory.newConnection(); Channel channel = getChannel(connection)) {
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, json.getBytes());
        }
    }

    private Channel getChannel(Connection connection) throws IOException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        return channel;
    }

    @Override
    public void save(Person person) throws IOException, TimeoutException {
        String json = pack(SAVE, person);
        sendMessage(json);
    }

    @Override
    public void delete(Person person) throws IOException, TimeoutException {
        String json = pack(DELETE, person);
        sendMessage(json);
    }

    private String pack(String command, Person person) throws JsonProcessingException {
        Map<String, Person> mapPerson = new HashMap<>();
        mapPerson.put(command, person);
        return objectMapper.writeValueAsString(mapPerson);
    }
}
