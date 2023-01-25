package com.beassessment.stocksmanager.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    private final String stocksKafkaTopic;

    private final int stocksKafkaTopicPartitions;

    private final int stocksKafkaTopicReplicas;

    private final String bootstrapServer;

    private final String autoOffsetResetConfig;

    public KafkaConfig(@Value("${stocks.kafka.topic}") String stocksKafkaTopic, @Value("${stocks.kafka.topic.partitions}") int stocksKafkaTopicPartitions, @Value("${stocks.kafka.topic.replicas}") int stocksKafkaTopicReplicas, @Value("${spring.kafka.bootstrap-servers}") String bootstrapServer, @Value("${spring.kafka.consumer.auto-offset-reset}") String autoOffsetResetConfig) {
        this.stocksKafkaTopic = stocksKafkaTopic;
        this.stocksKafkaTopicPartitions = stocksKafkaTopicPartitions;
        this.stocksKafkaTopicReplicas = stocksKafkaTopicReplicas;
        this.bootstrapServer = bootstrapServer;
        this.autoOffsetResetConfig = autoOffsetResetConfig;
    }

    @Bean(name = "stringStringProducerFactory")
    public ProducerFactory<String, String> initializeStringStringProducerFactory() {
        Map<String, Object> kafkaProducerConfig = new HashMap<>();
        kafkaProducerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        kafkaProducerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProducerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfig);
    }

    @Bean(name = "stringStringKafkaTemplate")
    public KafkaTemplate<String, String> initializeStringStringKafkaTemplate(@Qualifier("stringStringProducerFactory") ProducerFactory<String, String> stringStringProducerFactory) {
        return new KafkaTemplate<>(stringStringProducerFactory);
    }

    @Bean(name = "stringStringConsumerFactory")
    public ConsumerFactory<String, String> initializeStringStringConsumerFactory() {
        Map<String, Object> kafkaConsumerConfig = new HashMap<>();
        kafkaConsumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.bootstrapServer);
        kafkaConsumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConsumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConsumerConfig.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, this.autoOffsetResetConfig);
        return new DefaultKafkaConsumerFactory<>(kafkaConsumerConfig);
    }

    @Bean(name = "stringStringConcurrentKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> initializeStringStringConcurrentKafkaListenerContainerFactory(@Qualifier("stringStringConsumerFactory") ConsumerFactory<String, String> stringStringConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, String> concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory<>();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(stringStringConsumerFactory);
        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    public NewTopic initializeStocksTopic() {
        return TopicBuilder.name(this.stocksKafkaTopic)
                .partitions(this.stocksKafkaTopicPartitions)
                .replicas(this.stocksKafkaTopicReplicas)
                .build();
    }
}
