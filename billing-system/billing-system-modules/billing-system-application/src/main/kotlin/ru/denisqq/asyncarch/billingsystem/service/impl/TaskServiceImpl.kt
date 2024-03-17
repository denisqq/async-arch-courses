package ru.denisqq.asyncarch.billingsystem.service.impl

import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.denisqq.asyncarch.billingsystem.config.KafkaTopicProperties
import ru.denisqq.asyncarch.billingsystem.model.Task
import ru.denisqq.asyncarch.billingsystem.model.TaskStatus
import ru.denisqq.asyncarch.billingsystem.model.TaskStatus.COMPLETED
import ru.denisqq.asyncarch.billingsystem.repository.TaskRepository
import ru.denisqq.asyncarch.billingsystem.service.TaskService
import ru.denisqq.asyncarch.billingsystem.service.UserService
import ru.denisqq.asyncarch.tasktracker.TaskChanged
import ru.denisqq.asyncarch.tasktracker.TaskCompleted
import ru.denisqq.asyncarch.tasktracker.TaskPriceChanged
import java.math.BigDecimal
import java.util.UUID
import kotlin.random.Random


@Service
class TaskServiceImpl(
    private val taskRepository: TaskRepository,
    private val userService: UserService,
    private val kafkaTemplate: KafkaTemplate<String, SpecificRecord>,
    private val topicProperties: KafkaTopicProperties,
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

        kafkaTemplate.send(
            ProducerRecord(
                topicProperties.taskCudStreaming,
                null,
                TaskPriceChanged.newBuilder()
                    .setEventId(UUID.randomUUID().toString())
                    .setTaskIntegrationId(task.integrationId)
                    .setAssignTaskCost(task.assignTaskCost)
                    .setCompleteTaskCost(task.completeTaskCost)
                    .build()
            )
        )
        return taskRepository.save(task)
    }

    override fun existByIntegrationId(taskIntegrationId: String): Boolean {
        return taskRepository.existsTaskByIntegrationId(taskIntegrationId)
    }

    @Transactional
    override fun completeTask(taskCompleted: TaskCompleted) {
        val task = taskRepository.findTaskByIntegrationId(taskCompleted.taskIntegrationId)?.let {
            it.taskStatus = COMPLETED
            it
        } ?: run {
            Task(
                integrationId = taskCompleted.taskIntegrationId,
                taskStatus = COMPLETED
            )
        }
        taskRepository.save(task)
    }

    override fun findByIntegrationId(taskIntegrationId: String): Task? {
        return taskRepository.findTaskByIntegrationId(taskIntegrationId)
    }

    override fun create(taskIntegrationId: String): Task {
        return taskRepository.save(
            Task(integrationId = taskIntegrationId)
        )
    }

}