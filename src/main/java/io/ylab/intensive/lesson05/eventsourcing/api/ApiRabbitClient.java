package io.ylab.intensive.lesson05.eventsourcing.api;

import io.ylab.intensive.lesson05.eventsourcing.Person;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Denis Zolotarev
 */
public interface ApiRabbitClient {
    void save(Person person) throws IOException, TimeoutException;

    void delete(Person person) throws IOException, TimeoutException;
}
