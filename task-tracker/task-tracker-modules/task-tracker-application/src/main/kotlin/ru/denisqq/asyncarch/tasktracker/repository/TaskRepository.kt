package ru.denisqq.asyncarch.tasktracker.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.denisqq.asyncarch.tasktracker.model.Task
import ru.denisqq.asyncarch.tasktracker.model.TaskStatus
import java.util.*

interface TaskRepository: JpaRepository<Task, UUID> {

    fun findTasksByTaskStatus(taskStatus: TaskStatus): List<Task>
}