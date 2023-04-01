package io.ylab.intensive.lesson04.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson04.DbUtil;
import io.ylab.intensive.lesson04.RabbitMQUtil;
import io.ylab.intensive.lesson04.eventsourcing.Command;
import io.ylab.intensive.lesson04.eventsourcing.Person;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static io.ylab.intensive.lesson04.eventsourcing.QueueParams.QUEUE_NAME;


public class DbApp {
    private static final String DELETE_PERSON_QUERY = "DELETE FROM person WHERE person_id=?";
    private static final String UPDATE_PERSON_QUERY = "UPDATE person SET first_name=?, last_name=?, middle_name=? WHERE person_id=?";
    private static final String INSERT_PERSON_QUERY = "INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES (?, ?, ?, ?);";
    private static final String EXISTS_QUERY = "SELECT * FROM person WHERE person_id=?";

    private DataSource dataSource;
    private ConnectionFactory connectionFactory;

    private Command commandHandler = new Command();

    public DbApp(DataSource dataSource, ConnectionFactory connectionFactory) {
        this.dataSource = dataSource;
        this.connectionFactory = connectionFactory;
    }

    public static void main(String[] args) throws Exception {
        // тут пишем создание и запуск приложения работы с БД
        try {
            DbApp app = new DbApp(initDb(), initMQ());
            app.start();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }

    public void start() throws Exception {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(QUEUE_NAME, true);
                if (message != null) {
                    String received = new String(message.getBody());
                    Map<String, Person> commandMap = commandHandler.unpack(received);
                    for (Map.Entry<String, Person> command : commandMap.entrySet()) {
                        switch (command.getKey()) {
                            case Command.SAVE: {
                                savePersonToDB(command.getValue());
                                break;
                            }
                            case Command.DELETE: {
                                deletePersonFromDB(command.getValue().getId());
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
        }
    }

    private void deletePersonFromDB(Long personId) throws SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(DELETE_PERSON_QUERY)) {
            preparedStatement.setLong(1, personId);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Person deleted!");
            } else {
                System.err.println("Person not found");
            }
        }
    }

    private void savePersonToDB(Person person) throws SQLException {
        if (personExists(person.getId())) {
            updatePerson(person);
        } else {
            insertPerson(person);
        }
    }

    private void updatePerson(Person person) throws SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(UPDATE_PERSON_QUERY)) {
            preparedStatement.setString(1, person.getName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setString(3, person.getMiddleName());
            preparedStatement.setLong(4, person.getId());
            preparedStatement.executeUpdate();
            System.out.println("Person updated!");
        }
    }

    private void insertPerson(Person person) throws SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_PERSON_QUERY)) {
            preparedStatement.setLong(1, person.getId());
            preparedStatement.setString(2, person.getName());
            preparedStatement.setString(3, person.getLastName());
            preparedStatement.setString(4, person.getMiddleName());
            preparedStatement.executeUpdate();
            System.out.println("Person added!");
        }
    }

    public boolean personExists(Long personId) throws SQLException {

        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(EXISTS_QUERY)) {
            preparedStatement.setLong(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
    }
}
