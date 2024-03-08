package ru.denisqq.asyncarch.tasktracker.outbox.events

import ru.denisqq.asyncarch.tasktracker.model.Task

fun Task.toChangedEvent() = TaskChangedEvent(
    taskId = this.id!!,
    title = this.title,
    description = this.description,
    assignUserId = this.assign.id!!,
    taskStatus = this.taskStatus
)

fun List<Task>.toChangedEvent() = TaskChangedBatchEvent(
    changedTasks = this.map {
        it.toChangedEvent()
    }
)


fun Task.toCreatedEvent() = TaskCreatedEvent(
    taskId = this.id!!,
    assignUserId = this.assign.id!!
)

fun Task.toAssignedEvent() = TaskAssignedEvent(
    taskId = this.id!!,
    assignUserId = this.assign.id!!
)

fun List<Task>.toShuffledEvent() = TaskShuffledEvent(
    taskAssignedEvents = this.map {
        TaskAssignedEvent(
            taskId = it.id!!,
            assignUserId = it.assign.id!!
        )
    }
)

fun Task.toCompletedTaskEvent() = TaskCompletedEvent(
    taskId = this.id!!,
    assignUserId = this.assign.id!!
)