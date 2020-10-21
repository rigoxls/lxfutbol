package com.lxfutbol.integrator.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.RoutingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;

import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;

@Component
public class KafkaIntegratorSender {

	private final Logger LOG = LoggerFactory.getLogger(KafkaIntegratorSender.class);

	private KafkaTemplate<String, String> kafkaTemplate;
	//private RoutingKafkaTemplate routingKafkaTemplate;
	    
	@Autowired
    private ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate;

	public String sendMessageWithCallback(String message, String topicName) throws InterruptedException, ExecutionException {
		
		ProducerRecord<String, String> record = new ProducerRecord<>(topicName, null, message, message);
		RequestReplyFuture<String, String, String> future = replyingKafkaTemplate.sendAndReceive(record);
		ConsumerRecord<String, String> response = future.get();
		
		LOG.info("***************************************-");
		LOG.info("***************************************-");
		System.out.println(response.value());
		LOG.info("***************************************-");
		LOG.info("***************************************-");
		return response.value();
	}

}
