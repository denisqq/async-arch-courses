package ru.denisqq.asyncarch.tasktracker.web

import org.springframework.web.bind.annotation.*
import ru.denisqq.asyncarch.tasktracker.service.TaskService
import ru.denisqq.asyncarch.tasktracker.web.request.TaskCreateRequest
import ru.denisqq.asyncarch.tasktracker.web.response.TaskResponse
import ru.denisqq.asyncarch.tasktracker.web.response.toDto
import java.util.*

@RestController
@RequestMapping(path = ["/v1/tasks"])
class TaskController(
    private val taskService: TaskService
) {
    @GetMapping
    fun findTasks(): List<TaskResponse> {
        return taskService.findAll().toDto()
    }

    @PostMapping("/shuffle")
    fun shuffle(): List<TaskResponse> {
        return taskService.shuffle().toDto()
    }

    @PostMapping
    fun createTask(@RequestBody request: TaskCreateRequest): TaskResponse {
        return taskService.create(request).toDto()
    }

    @PutMapping("/{taskId}/complete")
    fun completeTask(@PathVariable taskId: UUID): TaskResponse {
        return taskService.completeTask(taskId).toDto()
    }
}