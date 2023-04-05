package io.ylab.intensive.lesson05.eventsourcing.db;

import com.rabbitmq.client.Channel;

/**
 * @author Denis Zolotarev
 */
public interface MessageProcessor {

    void start() throws Exception;

    void processSingleMessage(Channel channel) throws Exception;
}
