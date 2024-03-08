package ru.denisqq.asyncarch.tasktracker.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.denisqq.asyncarch.tasktracker.model.Task
import ru.denisqq.asyncarch.tasktracker.model.TaskStatus
import ru.denisqq.asyncarch.tasktracker.model.TaskStatus.OPEN
import ru.denisqq.asyncarch.tasktracker.model.User
import ru.denisqq.asyncarch.tasktracker.repository.TaskRepository
import ru.denisqq.asyncarch.tasktracker.repository.findOne
import ru.denisqq.asyncarch.tasktracker.outbox.OutboxService
import ru.denisqq.asyncarch.tasktracker.outbox.events.*
import ru.denisqq.asyncarch.tasktracker.service.TaskService
import ru.denisqq.asyncarch.tasktracker.service.UserService
import ru.denisqq.asyncarch.tasktracker.web.request.TaskCreateRequest
import java.util.*

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val outboxService: OutboxService,
    private val userService: UserService
) : TaskService {
    @Transactional
    override fun create(taskRequest: TaskCreateRequest): Task {
        val devUsers = userService.findDevs()
        val randomDev = devUsers.random()

        val task = taskRepository.save(
            taskRequest.mapToEntity(randomDev)
        )

        outboxService.createRecord(task.toChangedEvent())
        outboxService.createRecord(task.toCreatedEvent())
        outboxService.createRecord(task.toAssignedEvent())

        return task
    }

    override fun findAll(): List<Task> {
        return taskRepository.findAll()
    }

    @Transactional
    override fun shuffle(): List<Task> {
        val openedTasks = taskRepository.findTasksByTaskStatus(OPEN)
        val devUsers = userService.findDevs()

        val changedTasks = taskRepository.saveAll(
            openedTasks.map {
                it.assign = devUsers.random()
                it
            }
        )

        outboxService.createRecord(changedTasks.toShuffledEvent())
        outboxService.createRecord(changedTasks.toChangedEvent())

        return changedTasks
    }

    @Transactional
    override fun completeTask(taskId: UUID): Task {
        val completedTask = taskRepository.findOne(taskId).also {
            it.taskStatus = TaskStatus.COMPLETED
            taskRepository.save(it)
        }

        outboxService.createRecord(completedTask.toChangedEvent())
        outboxService.createRecord(completedTask.toCompletedTaskEvent())
        return completedTask
    }
}


private fun TaskCreateRequest.mapToEntity(dev: User): Task {
    return Task(
        title = this.title,
        description = this.title,
        taskStatus = OPEN,
        assign = dev
    )
}