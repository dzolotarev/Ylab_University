package io.ylab.intensive.lesson05.eventsourcing.api;


import io.ylab.intensive.lesson05.eventsourcing.Person;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Denis Zolotarev
 */
@Component
public class PersonApiImpl implements PersonApi {
    ApiDbClient appDbClient;
    ApiRabbitClient apiRabbitClient;

    public PersonApiImpl(ApiDbClient appDbClient, ApiRabbitClient apiRabbitClient) {
        this.appDbClient = appDbClient;
        this.apiRabbitClient = apiRabbitClient;
    }

    @Override
    public void deletePerson(Long personId) throws IOException, TimeoutException {
        Person person = new Person();
        person.setId(personId);
        apiRabbitClient.delete(person);
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) throws IOException, TimeoutException {
        Person person = new Person(personId, firstName, lastName, middleName);
        apiRabbitClient.save(person);
    }

    @Override
    public Person findPerson(Long personId) throws SQLException {
        return appDbClient.find(personId);
    }

    @Override
    public List<Person> findAll() throws SQLException {
        return appDbClient.findAll();
    }
}
