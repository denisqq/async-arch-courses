package ru.denisqq.asyncarch.billingsystem.analytics.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.denisqq.asyncarch.billingsystem.analytics.service.AnalyticsTransactionService
import ru.denisqq.asyncarch.tasktracker.TransactionCreated

@Component
class AnalyticsTransactionConsumer(
    private val analyticsTransactionService: AnalyticsTransactionService
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.transaction-streaming}"],
    )
    fun transactionCreatedConsume(
        @Payload transactionCreated: TransactionCreated,
    ) {
        analyticsTransactionService.create(transactionCreated)
    }
}