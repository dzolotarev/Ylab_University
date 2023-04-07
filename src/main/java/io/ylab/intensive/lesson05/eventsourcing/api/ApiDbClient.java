package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Denis Zolotarev
 */
public interface ApiDbClient {

    Person find(Long personId) throws SQLException;

    List<Person> findAll() throws SQLException;
}
