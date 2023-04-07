package io.ylab.intensive.lesson05.messagefilter;

/**
 * @author Denis Zolotarev
 */
public interface QueueParams {
    String EXCHANGE_NAME = "filter";
    String ROUTING_KEY = "message_filter";
    String INPUT_QUEUE = "input";
    String OUTPUT_QUEUE = "output";
}
