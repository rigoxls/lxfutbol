package com.lxfutbol.transport.kafka.Sender;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class KafkaSenderTopicConfig {

	@Value("${com.lxfutbol.integrator.kafka.topic-4}")
	private String topic4;


	@Bean
	NewTopic topic4() {
		return TopicBuilder.name(topic4).build();
	}
	
}
