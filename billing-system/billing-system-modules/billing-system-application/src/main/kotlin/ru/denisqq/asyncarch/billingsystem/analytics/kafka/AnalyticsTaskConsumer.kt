package ru.denisqq.asyncarch.billingsystem.analytics.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.denisqq.asyncarch.billingsystem.analytics.service.AnalyticsTaskService
import ru.denisqq.asyncarch.tasktracker.TaskChanged
import ru.denisqq.asyncarch.tasktracker.TaskPriceChanged

@Component
class AnalyticsTaskConsumer(
    private val analyticsTaskService: AnalyticsTaskService,
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.task-cud-streaming}"],
    )
    fun taskChangedConsume(
        @Payload taskChanged: TaskChanged,
    ) {
        analyticsTaskService.createOrUpdate(taskChanged)
    }

    @KafkaListener(
        topics = ["\${spring.kafka.topics.task-cud-streaming}"],
    )
    fun taskPriceChanged(
        @Payload taskPriceChanged: TaskPriceChanged
    ) {
        analyticsTaskService.updatePrice(taskPriceChanged)
    }
}