package ru.denisqq.asyncarch.tasktracker.web.request

data class TaskCreateRequest(
    val title: String,
    val description: String
)