package ru.denisqq.asyncarch.tasktracker.service

import ru.denisqq.asyncarch.tasktracker.model.Task
import ru.denisqq.asyncarch.tasktracker.web.request.TaskCreateRequest
import java.util.*

interface TaskService {
    fun create(taskRequest: TaskCreateRequest): Task

    fun findAll(): List<Task>
    fun shuffle(): List<Task>

    fun completeTask(taskId: UUID): Task

}