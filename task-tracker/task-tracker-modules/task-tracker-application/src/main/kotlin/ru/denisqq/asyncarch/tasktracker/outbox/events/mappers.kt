package ru.denisqq.asyncarch.tasktracker.outbox.events

import ru.denisqq.asyncarch.tasktracker.*
import ru.denisqq.asyncarch.tasktracker.model.Task
import java.util.*
import ru.denisqq.asyncarch.tasktracker.taskStatus as TaskStatusAvroEnum

fun Task.toChangedEvent(): TaskChanged = TaskChanged.newBuilder()
    .setEventId(UUID.randomUUID().toString())
    .setTaskIntegrationId(this.integrationId)
    .setTaskStatus(TaskStatusAvroEnum.valueOf(this.taskStatus.name))
    .setAssignUserSsoId(this.assign.userSsoId)
    //TODO Сделать
    .setJiraId(this.jiraId)
    .build()

fun Task.toAssignedEvent(): TaskAssigned = TaskAssigned.newBuilder()
    .setEventId(UUID.randomUUID().toString())
    .setAssignUserSsoId(this.assign.userSsoId)
    .setTaskIntegrationId(this.integrationId)
    .build()

fun List<Task>.toShuffledEvent(): TaskShuffled = TaskShuffled.newBuilder()
    .setEventId(UUID.randomUUID().toString())
    .setAssignedTasks(
        this.map {
            TaskShuffleAssign.newBuilder()
                .setTaskIntegrationId(it.integrationId)
                .setAssignUserSsoId(it.assign.userSsoId)
                .build()
        }
    )
    .build()

fun Task.toCompletedTaskEvent(): TaskCompleted = TaskCompleted.newBuilder()
    .setEventId(UUID.randomUUID().toString())
    .setAssignUserSsoId(this.assign.userSsoId)
    .setTaskIntegrationId(this.integrationId)
    .build()