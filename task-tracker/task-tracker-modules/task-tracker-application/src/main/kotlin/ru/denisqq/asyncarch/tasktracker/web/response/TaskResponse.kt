package ru.denisqq.asyncarch.tasktracker.web.response

import ru.denisqq.asyncarch.tasktracker.model.Task
import java.util.*

data class TaskResponse(
    val id: UUID,
    val title: String,
    val description: String
)


fun List<Task>.toDto(): List<TaskResponse> = this.map {
    TaskResponse(
        id = it.id!!,
        title = it.title,
        description = it.description
    )
}

fun Task.toDto(): TaskResponse = TaskResponse(
    id = this.id!!,
    title = this.title,
    description = this.title
)