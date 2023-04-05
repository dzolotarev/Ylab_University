package io.ylab.intensive.lesson05.messagefilter;

import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

/**
 * @author Denis Zolotarev
 */
public interface MessageProcessor {

    void processInputMessage(Channel channel) throws IOException, SQLException, TimeoutException;

    void sendOutputMessage(String message) throws IOException, TimeoutException;

    void start() throws IOException, TimeoutException, InterruptedException, SQLException;
}

