package com.lxfutbol.spectacle.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * 
 * @author natalialeon
 *
 */
@Configuration
class KafkaTopicConfigLodge {

	@Value("${com.lxfutbol.spectacle.kafka.topic-2}")
	private String topic2;

	@Bean
	NewTopic topic2() {
		return TopicBuilder.name(topic2).build();
	}

}
