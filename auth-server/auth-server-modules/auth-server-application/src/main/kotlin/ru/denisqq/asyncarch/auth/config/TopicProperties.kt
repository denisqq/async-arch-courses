package ru.denisqq.asyncarch.auth.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("spring.kafka.topics")
data class TopicProperties(
    val usersBe: String
)