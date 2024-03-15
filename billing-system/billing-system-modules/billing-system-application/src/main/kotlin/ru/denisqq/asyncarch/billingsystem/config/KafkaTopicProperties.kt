package ru.denisqq.asyncarch.billingsystem.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.kafka.topics")
data class KafkaTopicProperties(
    val transactionStreaming: String,
    val taskCudStreaming: String,
)