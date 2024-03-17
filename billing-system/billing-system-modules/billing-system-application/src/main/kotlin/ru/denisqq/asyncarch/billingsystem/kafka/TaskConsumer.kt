package ru.denisqq.asyncarch.billingsystem.kafka

import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.annotation.RetryableTopic
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import org.springframework.transaction.support.TransactionTemplate
import ru.denisqq.asyncarch.billingsystem.service.TaskService
import ru.denisqq.asyncarch.billingsystem.service.TransactionService
import ru.denisqq.asyncarch.tasktracker.TaskAssigned
import ru.denisqq.asyncarch.tasktracker.TaskChanged
import ru.denisqq.asyncarch.tasktracker.TaskCompleted
import ru.denisqq.asyncarch.tasktracker.TaskCreated
import ru.denisqq.asyncarch.tasktracker.TaskShuffled

@Component
class TaskConsumer(
    private val taskService: TaskService,
    private val transactionService: TransactionService,
    private val transactionTemplate: TransactionTemplate,
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.task-workflow-events}"],
        errorHandler = "billingErrorHandler"
    )
    @RetryableTopic
    fun taskWorkflowConsumer(@Payload specificRecord: SpecificRecord) {
        when (specificRecord) {
            is TaskCreated -> {
                transactionService.debitTransaction(
                    idempotenceKey = specificRecord.eventId,
                    userSsoId = specificRecord.assignUserSsoId,
                    taskIntegrationId = specificRecord.taskIntegrationId,
                    details = "Списание за назначение задачи"
                )
            }

            is TaskCompleted -> {
                //Эти два вызова должны быть идемпотенты. Т.е не должно быть ситуации когда:
                // Назначали деньги, но при этом не закрыли задачу
                transactionTemplate.execute {
                    taskService.completeTask(specificRecord)
                    // Выполнится в транзакции, которую открыли выше
                    transactionService.creditTransaction(
                        idempotenceKey = specificRecord.eventId,
                        userSsoId = specificRecord.assignUserSsoId,
                        taskIntegrationId = specificRecord.taskIntegrationId,
                        details = "Назначение за выполнение задачи"
                    )
                }
            }

            is TaskShuffled -> {
                specificRecord.assignedTasks.forEach {
                    transactionService.debitTransaction(
                        idempotenceKey = specificRecord.eventId,
                        userSsoId = it.assignUserSsoId,
                        taskIntegrationId = it.taskIntegrationId,
                        details = "Назначение за выполнение задачи"
                    )
                }
            }
        }
    }

    @KafkaListener(
        topics = ["\${spring.kafka.topics.task-cud-streaming}"],
//        errorHandler = "fixedAttemptsErrorHandler"
    )
    fun taskChangesStreaming(@Payload taskChanged: TaskChanged) {
        if (!taskService.existByIntegrationId(taskChanged.taskIntegrationId)) {
            taskService.create(taskChanged)
        }
    }
}