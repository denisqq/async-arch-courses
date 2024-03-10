package ru.denisqq.asyncarch.tasktracker.outbox

import org.apache.avro.specific.SpecificRecord
import ru.denisqq.asyncarch.tasktracker.outbox.events.OutboxEvent

interface OutboxService {
    fun <T : OutboxEvent> createRecord(outboxEvent: T)

    fun createRecord(outboxEvent: SpecificRecord)
}