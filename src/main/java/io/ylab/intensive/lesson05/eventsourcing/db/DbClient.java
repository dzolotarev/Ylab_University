package io.ylab.intensive.lesson05.eventsourcing.db;

import io.ylab.intensive.lesson05.eventsourcing.Person;

import java.sql.SQLException;

/**
 * @author Denis Zolotarev
 */
public interface DbClient {

    void save(Person person) throws SQLException;

    void delete(Long personId) throws SQLException;
}
