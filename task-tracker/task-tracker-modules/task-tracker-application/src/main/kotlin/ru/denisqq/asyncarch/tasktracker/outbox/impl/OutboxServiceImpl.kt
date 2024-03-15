package ru.denisqq.asyncarch.tasktracker.outbox.impl

import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import ru.denisqq.asyncarch.tasktracker.TaskChanged
import ru.denisqq.asyncarch.tasktracker.TaskCompleted
import ru.denisqq.asyncarch.tasktracker.TaskCreated
import ru.denisqq.asyncarch.tasktracker.TaskShuffled
import ru.denisqq.asyncarch.tasktracker.config.TopicsProperties
import ru.denisqq.asyncarch.tasktracker.outbox.OutboxService

@Service
class OutboxServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String?, SpecificRecord>,
    private val props: TopicsProperties,
) : OutboxService {

    //    @Scheduled(fixedDelayString = "1000")
//    fun processRecords() {
//
//    }
//
    // TODO пишем в БД, а после выгребаем и отправляем
    // Писать в БД, только Business Events

    override fun createRecord(outboxEvent: SpecificRecord) {
        val topic = when (outboxEvent) {
            is TaskShuffled, is TaskCompleted, is TaskCreated -> props.taskWorkflowEvents
            is TaskChanged -> props.taskCudStreaming
            else -> throw IllegalArgumentException("unknown event")
        }
        val producerRecord = ProducerRecord<String?, SpecificRecord>(
            topic, null, outboxEvent
        )

        kafkaTemplate.send(producerRecord).get()
    }
}