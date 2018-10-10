package com.adks.dbclient.kafka;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adks.dbclient.kafka.message.IMessageProducer;
import com.alibaba.fastjson.JSON;

/**
 * Kafka生产者实现
 */
public class KafkaMessageProducer implements IMessageProducer {
	private static final Logger logger = LoggerFactory.getLogger(KafkaMessageProducer.class);
	
	private  Producer<String, String> producer;
	
	@Override
	public boolean send(String topic, Object message) {
		String targetMsg;
		try {
			if (message instanceof String) {
				targetMsg = (String) message;
			} else {
				targetMsg = JSON.toJSONString(message);
			}
			producer.send(new ProducerRecord<String, String>(topic, targetMsg));
		} catch (Exception e) {
			logger.error("send message failed, topic:{}, message:{}", e);
			return false;
		}
		return true;
	}

	public Producer<String, String> getProducer() {
		return producer;
	}

	public void setProducer(Producer<String, String> producer) {
		this.producer = producer;
	}

}
