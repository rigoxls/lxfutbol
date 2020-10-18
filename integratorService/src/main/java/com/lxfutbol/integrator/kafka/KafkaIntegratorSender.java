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

@Component
public class KafkaIntegratorSender {

	private final Logger LOG = LoggerFactory.getLogger(KafkaIntegratorSender.class);

	private KafkaTemplate<String, String> kafkaTemplate;
	private RoutingKafkaTemplate routingKafkaTemplate;
	

	@Autowired
	KafkaIntegratorSender(KafkaTemplate<String, String> kafkaTemplate, RoutingKafkaTemplate routingKafkaTemplate,
			KafkaTemplate<String, String> userKafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
		this.routingKafkaTemplate = routingKafkaTemplate;		
	}

	public void sendMessage(String message, String topicName) {
		LOG.info("Sending : {}", message);
		LOG.info("--------------------------------");

		kafkaTemplate.send(topicName, message);
	}

	void sendWithRoutingTemplate(String message, String topicName) {
		LOG.info("Sending : {}", message);
		LOG.info("--------------------------------");

		routingKafkaTemplate.send(topicName, message.getBytes());
	}

	void sendCustomMessage(String user, String topicName) {
		LOG.info("Sending Json Serializer : {}", user);
		LOG.info("--------------------------------");

		kafkaTemplate.send(topicName, user);
	}

	public void sendMessageWithCallback(String message, String topicName) {
		LOG.info("Sending : {}", message);
		LOG.info("---------------------------------");

		ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				LOG.info("Callback returned:" + result.toString());
				/*LOG.info("Success Callback: [{}] delivered with offset -{}", message,
						result.getRecordMetadata().offset());*/
			}

			@Override
			public void onFailure(Throwable ex) {
				LOG.warn("Failure Callback: Unable to deliver message [{}]. {}", message, ex.getMessage());
			}
		});
	}

}
