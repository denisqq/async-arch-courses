package ru.denisqq.asyncarch.tasktracker.outbox.impl

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.denisqq.asyncarch.tasktracker.config.TopicsProperties
import ru.denisqq.asyncarch.tasktracker.outbox.OutboxService
import ru.denisqq.asyncarch.tasktracker.outbox.events.OutboxEvent

@Service
class OutboxServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper,
    private val props: TopicsProperties
) : OutboxService {

    //    @Scheduled(fixedDelayString = "1000")
//    fun processRecords() {
//
//    }
//
    // TODO пишем в БД, а после выгребаем и отправляем
    override fun <T : OutboxEvent> createRecord(outboxEvent: T) {
        val producerRecord = ProducerRecord(
            props.destinationMap[outboxEvent.destination],
            outboxEvent.key,
            objectMapper.writeValueAsString(outboxEvent)
        )
        kafkaTemplate.send(producerRecord).get()
    }
}