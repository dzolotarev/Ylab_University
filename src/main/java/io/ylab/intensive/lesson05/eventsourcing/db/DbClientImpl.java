package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Denis Zolotarev
 */
@Component
public class DbClientImpl implements DbClient {
    private static final String DELETE_PERSON_QUERY = "DELETE FROM person WHERE person_id=?";
    private static final String UPDATE_PERSON_QUERY = "UPDATE person SET first_name=?, last_name=?, middle_name=? WHERE person_id=?";
    private static final String INSERT_PERSON_QUERY = "INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES (?, ?, ?, ?);";
    private static final String EXISTS_QUERY = "SELECT * FROM person WHERE person_id=?";

    private DataSource dataSource;

    public DbClientImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(Person person) throws SQLException {
        if (isPersonExist(person.getId())) {
            updatePerson(person);
        } else {
            insertPerson(person);
        }
    }

    @Override
    public void delete(Long personId) throws SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(DELETE_PERSON_QUERY)) {
            preparedStatement.setLong(1, personId);
            int rowCount = preparedStatement.executeUpdate();
            if (rowCount > 0) {
                System.out.println("Person deleted!");
            } else {
                System.err.println("Person not found!");
            }
        }
    }

    private boolean isPersonExist(Long personId) throws SQLException {
        try (PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(EXISTS_QUERY)) {
            preparedStatement.setLong(1, personId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
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
}
