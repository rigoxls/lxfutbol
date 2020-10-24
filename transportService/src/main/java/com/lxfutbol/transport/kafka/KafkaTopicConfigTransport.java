package com.lxfutbol.transport.kafka;

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
class KafkaTopicConfigTransport {

	@Value("${com.lxfutbol.transport.kafka.topic-3}")
	private String topic3;

	@Bean
	NewTopic topic3() {
		return TopicBuilder.name(topic3).build();
	}

}
