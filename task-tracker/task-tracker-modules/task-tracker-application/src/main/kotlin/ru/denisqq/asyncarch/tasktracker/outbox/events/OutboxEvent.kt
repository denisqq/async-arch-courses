package ru.denisqq.asyncarch.tasktracker.outbox.events

import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.denisqq.asyncarch.tasktracker.model.TaskStatus
import java.util.*


//TODO не используется, перешел на avro
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "eventName"
)
interface OutboxEvent {
    val destination: String
    val key: String?
        get() = null

    val eventId: String
        get() = UUID.randomUUID().toString()
}

interface BusinessEvent : OutboxEvent {
    override val destination: String
        get() = "task-workflow-events"
}

interface CUDEvent : OutboxEvent {
    override val destination: String
        get() = "task-cud-streaming"
}

data class TaskChangedEvent(
    val taskIntegrationId: String,
    val title: String,
    val description: String,
    val assignUserId: UUID,
    val taskStatus: TaskStatus
) : CUDEvent {
    override val key: String
        get() = this.taskIntegrationId.toString()
}

data class TaskChangedBatchEvent(
    val changedTasks: List<TaskChangedEvent>
) : CUDEvent

data class TaskCreatedEvent(
    val taskIntegrationId: String,
    val assignUserId: UUID
) : BusinessEvent {
    override val key: String
        get() = this.taskIntegrationId.toString()
}

data class TaskCompletedEvent(
    val taskIntegrationId: String,
    val assignUserId: UUID
) : BusinessEvent {
    override val key: String
        get() = this.taskIntegrationId.toString()
}

data class TaskAssignedEvent(
    val taskIntegrationId: String,
    val assignUserId: UUID
) : BusinessEvent {
    override val key: String
        get() = this.taskIntegrationId.toString()
}

data class TaskShuffledEvent(
//    val originatorId: UUID,
    val taskAssignedEvents: List<TaskAssignedEvent>
) : BusinessEvent {

}