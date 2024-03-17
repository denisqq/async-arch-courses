package ru.denisqq.asyncarch.billingsystem.service

import ru.denisqq.asyncarch.billingsystem.model.Task
import ru.denisqq.asyncarch.tasktracker.TaskChanged
import ru.denisqq.asyncarch.tasktracker.TaskCompleted

interface TaskService {
    fun create(taskChanged: TaskChanged): Task
    fun create(taskIntegrationId: String): Task

    fun findByIntegrationId(taskIntegrationId: String): Task?

    fun existByIntegrationId(taskIntegrationId: String): Boolean

    fun completeTask(taskCompleted: TaskCompleted)
}