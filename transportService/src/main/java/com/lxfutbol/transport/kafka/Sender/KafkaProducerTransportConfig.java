package com.lxfutbol.transport.kafka.Sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

public class KafkaProducerTransportConfig {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@Value("${com.lxfutbol.transport.kafka.bootstrap-servers}")
	private String bootstrapServers;
	
    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingKafkaTemplate(ProducerFactory<String, String> pf,
            ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        ConcurrentMessageListenerContainer<String, String> replyContainer = factory.createContainer("reply_transform-soap");
        replyContainer.getContainerProperties().setMissingTopicsFatal(false);
        replyContainer.getContainerProperties().setGroupId("transport-soap-group");
        return new ReplyingKafkaTemplate<>(pf, replyContainer);
    }	

    @Bean
    public KafkaTemplate<String, String> replyTemplate(ProducerFactory<String, String> pf,
            ConcurrentKafkaListenerContainerFactory<String, String> factory) {
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(pf);
        factory.getContainerProperties().setMissingTopicsFatal(false);
        factory.setReplyTemplate(kafkaTemplate);
        return kafkaTemplate;
    }
	
}
