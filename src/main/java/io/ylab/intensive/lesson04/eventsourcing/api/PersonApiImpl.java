package io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Command;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import static io.ylab.intensive.lesson04.eventsourcing.QueueParams.*;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {

    private static final String GET_PERSON_QUERY = "SELECT * FROM person WHERE person_id=?";
    private static final String GET_ALL_PERSONS_QUERY = "SELECT * FROM person";

    private DataSource dataSource;
    private ConnectionFactory connectionFactory;

    private Command commandHandler = new Command();

    public PersonApiImpl(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    private Channel getChannel(Connection connection) throws IOException, TimeoutException {
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, ROUTING_KEY);
        return channel;
    }

    @Override
    public void deletePerson(Long personId) throws IOException, TimeoutException {
        try (Connection connection = connectionFactory.newConnection(); Channel channel = getChannel(connection)) {
            Person person = new Person(personId, null, null, null);
            String message = commandHandler.pack(Command.DELETE, person);
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
        }
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) throws IOException, TimeoutException {
        try (Connection connection = connectionFactory.newConnection(); Channel channel = getChannel(connection)) {
            Person person = new Person(personId, firstName, lastName, middleName);
            String message = commandHandler.pack(Command.SAVE, person);
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes());
        }
    }

    @Override
    public Person findPerson(Long personId) throws SQLException {
        Person person = null;
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(GET_PERSON_QUERY)) {
            preparedStatement.setLong(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("person_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String middleName = resultSet.getString("middle_name");
                person = new Person(id, firstName, lastName, middleName);
            }
        }
        return person;
    }

    @Override
    public List<Person> findAll() throws SQLException {
        List<Person> persons = new ArrayList<>();
        try (Statement statement = dataSource.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_ALL_PERSONS_QUERY);
            while (resultSet.next()) {
                long id = resultSet.getLong("person_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String middleName = resultSet.getString("middle_name");
                persons.add(new Person(id, firstName, lastName, middleName));
            }
            return persons;
        }
    }
}
