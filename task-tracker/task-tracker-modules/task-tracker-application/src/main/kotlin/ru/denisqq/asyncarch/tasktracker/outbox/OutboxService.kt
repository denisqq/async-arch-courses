package ru.denisqq.asyncarch.tasktracker.outbox

import ru.denisqq.asyncarch.tasktracker.outbox.events.OutboxEvent

interface OutboxService {
    fun <T: OutboxEvent> createRecord(outboxEvent: T)
}