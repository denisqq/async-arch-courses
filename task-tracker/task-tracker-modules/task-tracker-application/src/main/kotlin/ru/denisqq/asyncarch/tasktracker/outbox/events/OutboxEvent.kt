package ru.denisqq.asyncarch.tasktracker.outbox.events

import com.fasterxml.jackson.annotation.JsonTypeInfo
import ru.denisqq.asyncarch.tasktracker.model.TaskStatus
import java.util.*

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "event_class")
//@JsonSubTypes({
//    @Type(value = Car.class, name = "car"),
//    @Type(value = Truck.class, name = "truck")
//})
interface OutboxEvent {
    val destination: String
    val key: String?
        get() = null
}

interface BusinessEvent : OutboxEvent {
    override val destination: String
        get() = "task-be-events"
}

interface CUDEvent : OutboxEvent {
    override val destination: String
        get() = "task-cud-streaming"
}

data class TaskChangedEvent(
    val taskId: UUID,
    val title: String,
    val description: String,
    val assignUserId: UUID,
    val taskStatus: TaskStatus
) : CUDEvent {
    override val key: String
        get() = this.taskId.toString()
}

data class TaskChangedBatchEvent(
    val changedTasks: List<TaskChangedEvent>
): CUDEvent

data class TaskCreatedEvent(
    val taskId: UUID,
    val assignUserId: UUID
) : BusinessEvent {
    override val key: String
        get() = this.taskId.toString()
}

data class TaskCompletedEvent(
    val taskId: UUID,
    val assignUserId: UUID
) : BusinessEvent {
    override val key: String
        get() = this.taskId.toString()
}

data class TaskAssignedEvent(
    val taskId: UUID,
    val assignUserId: UUID
): BusinessEvent {
    override val key: String
        get() = this.taskId.toString()
}

data class TaskShuffledEvent(
//    val originatorId: UUID,
    val taskAssignedEvents: List<TaskAssignedEvent>
): BusinessEvent {

}