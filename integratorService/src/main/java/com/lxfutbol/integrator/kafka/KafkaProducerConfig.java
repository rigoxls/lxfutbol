package com.lxfutbol.integrator.kafka;

import java.util.Map;
import java.util.HashMap;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

/**
 * KafkaProducerConfig
 */
@Configuration
class KafkaProducerConfig {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Value("${com.lxfutbol.integrator.kafka.bootstrap-servers}")
	private String bootstrapServers;

	@Bean
	Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		LOG.debug("***************************************************");
		LOG.debug("Paso por producerConfigs");
		
		return props;		
	}
	
	@Bean
	ProducerFactory<String, String> producerFactory() {
		System.out.println("***************************************************");
		System.out.println("Paso por ProducerFactory");
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}
	
    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        ConcurrentMessageListenerContainer<String, String> replyContainer = factory.createContainer("reply-integrator-provider");
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId("integrator_group_1");
        System.out.println("***************************************************");
        System.out.println("Paso por ReplyingKafkaTemplate");		
        return new ReplyingKafkaTemplate<>(producerFactory(), replyContainer);
    }

    @Bean
    public KafkaTemplate<String, String> replyTemplate(ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(producerFactory());
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        System.out.println("***************************************************");
        System.out.println("Paso por KafkaTemplate");
		
        return kafkaTemplate;
    }
}