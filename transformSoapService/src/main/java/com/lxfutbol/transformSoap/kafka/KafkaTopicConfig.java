package com.lxfutbol.transformSoap.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
class KafkaTopicConfig {

	@Value("${com.lxfutbol.transformSoap.kafka.topic-1}")
	private String topic1;

	@Bean
	NewTopic topic1() {
		return TopicBuilder.name(topic1).build();
	}

}
