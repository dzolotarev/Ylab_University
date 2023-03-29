package io.ylab.intensive.lesson04.eventsourcing;

/**
 * @author Denis Zolotarev
 */
public interface QueueParams {
    String EXCHANGE_NAME = "direct";
    String QUEUE_NAME = "queue";
    String ROUTING_KEY = "event_sourcing";
}
