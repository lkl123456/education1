package com.adks.dbclient.kafka.message;

/**
 * 消息处理者API
 */
public interface IMessageHandler {
	boolean handle(String topic, Object message);
}
