package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

@Component
public interface PersonApi {
    void deletePerson(Long personId) throws IOException, TimeoutException;

    void savePerson(Long personId, String firstName, String lastName, String middleName) throws IOException, TimeoutException;

    Person findPerson(Long personId) throws SQLException;

    List<Person> findAll() throws SQLException;
}
