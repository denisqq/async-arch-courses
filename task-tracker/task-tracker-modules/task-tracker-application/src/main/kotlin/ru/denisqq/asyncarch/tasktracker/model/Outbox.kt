package ru.denisqq.asyncarch.tasktracker.model

import jakarta.persistence.Entity
import ru.denisqq.asyncarch.tasktracker.outbox.events.OutboxEvent

@Entity
class Outbox(

//    val record: OutboxEvent
): AbstractEntity()