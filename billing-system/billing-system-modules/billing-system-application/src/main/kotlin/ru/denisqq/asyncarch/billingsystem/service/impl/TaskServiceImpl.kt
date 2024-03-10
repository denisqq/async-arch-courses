package ru.denisqq.asyncarch.billingsystem.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.denisqq.asyncarch.billingsystem.model.Task
import ru.denisqq.asyncarch.billingsystem.model.TaskStatus
import ru.denisqq.asyncarch.billingsystem.repository.TaskRepository
import ru.denisqq.asyncarch.billingsystem.service.TaskService
import ru.denisqq.asyncarch.billingsystem.service.UserService
import ru.denisqq.asyncarch.tasktracker.TaskChanged
import ru.denisqq.asyncarch.tasktracker.TaskCompleted
import java.math.BigDecimal
import kotlin.random.Random


@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val userService: UserService
) : TaskService {
    @Transactional
    override fun create(taskChanged: TaskChanged): Task {
        val task = Task(
            integrationId = taskChanged.taskIntegrationId,
            assignTaskCost = BigDecimal(Random.nextDouble(10.0, 20.0)),
            completeTaskCost = BigDecimal(Random.nextDouble(20.0, 40.0)),
            assign = userService.findBySsoId(taskChanged.assignUserSsoId),
            taskStatus = TaskStatus.valueOf(taskChanged.taskStatus.name)
        )

        return taskRepository.save(task)
    }

    override fun existByIntegrationId(taskIntegrationId: String): Boolean {
        return taskRepository.existsTaskByIntegrationId(taskIntegrationId)
    }

    override fun completeTask(taskCompleted: TaskCompleted) {
        TODO("Not yet implemented")
    }

    override fun findByIntegrationId(taskIntegrationId: String): Task {
        return taskRepository.findTaskByIntegrationId(taskIntegrationId)
    }

}