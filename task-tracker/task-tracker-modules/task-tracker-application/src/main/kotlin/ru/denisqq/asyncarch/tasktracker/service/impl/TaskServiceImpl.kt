package ru.denisqq.asyncarch.tasktracker.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.denisqq.asyncarch.tasktracker.model.Task
import ru.denisqq.asyncarch.tasktracker.model.TaskStatus
import ru.denisqq.asyncarch.tasktracker.model.TaskStatus.OPEN
import ru.denisqq.asyncarch.tasktracker.model.User
import ru.denisqq.asyncarch.tasktracker.outbox.OutboxService
import ru.denisqq.asyncarch.tasktracker.outbox.events.toChangedEvent
import ru.denisqq.asyncarch.tasktracker.outbox.events.toCompletedTaskEvent
import ru.denisqq.asyncarch.tasktracker.outbox.events.toCreatedEvent
import ru.denisqq.asyncarch.tasktracker.outbox.events.toShuffledEvent
import ru.denisqq.asyncarch.tasktracker.repository.TaskRepository
import ru.denisqq.asyncarch.tasktracker.repository.findOne
import ru.denisqq.asyncarch.tasktracker.service.TaskService
import ru.denisqq.asyncarch.tasktracker.service.UserService
import ru.denisqq.asyncarch.tasktracker.web.request.TaskCreateRequest
import java.util.*

@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val outboxService: OutboxService,
    private val userService: UserService,
) : TaskService {
    @Transactional
    override fun create(taskRequest: TaskCreateRequest): Task {
        val devUsers = userService.findDevs()
        val randomDev = devUsers.random()

        val task = taskRepository.save(
            taskRequest.mapToEntity(randomDev)
        )

        outboxService.createRecord(task.toCreatedEvent())
        outboxService.createRecord(task.toChangedEvent())

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
        //TODO Надо ли тут отправку?
//        outboxService.createRecord(changedTasks.toChangedEvent())

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
    // Разобьает на 4 группы, можно упростить
    val matchGroup = JIRA_ID_PATTERN.find(this.title)!!.groupValues

    return Task(
        integrationId = UUID.randomUUID().toString(),
        title = matchGroup[3],
        description = this.title,
        taskStatus = OPEN,
        assign = dev,
        jiraId = matchGroup[1]
    )
}


val JIRA_ID_PATTERN = Regex(
    "(.+) (-) (.+)"
)