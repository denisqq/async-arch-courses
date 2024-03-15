package ru.denisqq.asyncarch.tasktracker.outbox

import org.apache.avro.specific.SpecificRecord

interface OutboxService {
    fun createRecord(outboxEvent: SpecificRecord)
}