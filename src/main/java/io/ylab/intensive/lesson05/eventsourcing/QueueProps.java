package io.ylab.intensive.lesson05.eventsourcing;

/**
 * @author Denis Zolotarev
 */
public interface QueueProps {
    String EXCHANGE_NAME = "direct";
    String QUEUE_NAME = "queue";
    String ROUTING_KEY = "event_sourcing";
}
