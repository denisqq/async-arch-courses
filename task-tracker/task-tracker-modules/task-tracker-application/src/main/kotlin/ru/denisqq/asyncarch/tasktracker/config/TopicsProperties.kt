package ru.denisqq.asyncarch.tasktracker.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.kafka.topics")
data class TopicsProperties(
    val taskCudStreaming: String,
    val taskWorkflowEvents: String
)