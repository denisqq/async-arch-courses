package ru.denisqq.asyncarch.billingsystem.analytics.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.denisqq.asyncarch.authserver.UserRegistered
import ru.denisqq.asyncarch.billingsystem.analytics.service.AnalyticsUserService

@Component
class AnalyticsUserConsumer(
    private val analyticsUserService: AnalyticsUserService
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.users-registered}"]
    )
    fun userRegisteredConsume(@Payload userRegisteredEvent: UserRegistered) {
        analyticsUserService.create(userRegisteredEvent)
    }

}