package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Zolotarev
 */
@Component
public class ApiDbClientImpl implements ApiDbClient {
    private static final String GET_PERSON_QUERY = "SELECT * FROM person WHERE person_id=?";
    private static final String GET_ALL_PERSONS_QUERY = "SELECT * FROM person";

    private DataSource dataSource;


    public ApiDbClientImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Person find(Long personId) throws SQLException {
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
