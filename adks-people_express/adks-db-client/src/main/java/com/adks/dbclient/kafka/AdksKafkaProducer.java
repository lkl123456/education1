package com.adks.dbclient.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;

/**
 * @author panpanxu
 */
public class AdksKafkaProducer extends KafkaProducer<String, String> {

	public AdksKafkaProducer(Map<String, Object> configs) {
		super(configs);
	}

}
