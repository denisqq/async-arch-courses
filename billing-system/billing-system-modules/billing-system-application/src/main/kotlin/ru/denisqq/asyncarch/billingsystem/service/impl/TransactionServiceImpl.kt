package ru.denisqq.asyncarch.billingsystem.service.impl

import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.denisqq.asyncarch.billingsystem.config.KafkaTopicProperties
import ru.denisqq.asyncarch.billingsystem.model.TransactionLog
import ru.denisqq.asyncarch.billingsystem.repository.TransactionLogRepository
import ru.denisqq.asyncarch.billingsystem.service.TaskService
import ru.denisqq.asyncarch.billingsystem.service.TransactionService
import ru.denisqq.asyncarch.billingsystem.service.UserService
import ru.denisqq.asyncarch.tasktracker.TransactionCreated
import java.time.Instant
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.util.UUID

@Service
class TransactionServiceImpl(
    private val userService: UserService,
    private val taskService: TaskService,
    private val transactionLogRepository: TransactionLogRepository,
    private val kafkaTemplate: KafkaTemplate<String, SpecificRecord>,
    private val topicProperties: KafkaTopicProperties,
) : TransactionService {
    @Transactional
    override fun creditTransaction(
        idempotenceKey: String,
        userSsoId: String,
        taskIntegrationId: String,
        details: String,
    ) {
        val user = userService.findBySsoId(userSsoId)
        val task = taskService.findByIntegrationId(taskIntegrationId)

        val transactionLog = TransactionLog(
            idempotenceKey = idempotenceKey,
            user = user,
            credit = task.completeTaskCost,
            details = details,
            task = task,
            billingCycle = user.billingCycle
        )
        applyTransaction(transactionLog)
    }

    @Transactional
    override fun debitTransaction(
        idempotenceKey: String,
        userSsoId: String,
        taskIntegrationId: String,
        details: String,
    ) {
        val user = userService.findBySsoId(userSsoId)
        val task = taskService.findByIntegrationId(taskIntegrationId)

        val transactionLog = TransactionLog(
            idempotenceKey = idempotenceKey,
            user = user,
            debit = task.assignTaskCost,
            details = details,
            task = task,
            billingCycle = user.billingCycle,
            billingCycleClosed = false
        )

        applyTransaction(transactionLog)
    }

    private fun applyTransaction(transactionLog: TransactionLog) {
        val currentBalance = transactionLog.user.balance

        transactionLog.credit?.let {
            currentBalance.plus(it)
        }

        transactionLog.debit?.let {
            currentBalance.minus(it)
        }

        userService.updateBalance(userSsoId = transactionLog.user.userSsoId, balance = currentBalance)
        transactionLogRepository.save(transactionLog)

        val transactionCreatedEvent = TransactionCreated.newBuilder()
            .setEventId(UUID.randomUUID().toString())
            .setDebit(transactionLog.debit)
            .setCredit(transactionLog.credit)
            .setTaskIntegrationId(transactionLog.task.integrationId)
            .setUserId(transactionLog.user.userSsoId)
            .setDetails(transactionLog.details)
            .setIntegrationId(transactionLog.integrationId)
            .setTransactionProcessedAt(Instant.now())
//            .setTransactionProcessingDate(Instant.now())
            .build()

        kafkaTemplate.send(
            ProducerRecord(topicProperties.transactionStreaming, null, transactionCreatedEvent)
        ).get()
    }
}