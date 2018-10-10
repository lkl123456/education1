package com.adks.dbclient.kafka.message;

/**
 * 消息消费者
 *
 */
public interface IMessageConsumer {
	void consume(String topic, IMessageHandler handler);
	boolean start();
	boolean stop();
}
