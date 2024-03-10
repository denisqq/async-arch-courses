package ru.denisqq.asyncarch.billingsystem.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.denisqq.asyncarch.billingsystem.model.TransactionLog
import ru.denisqq.asyncarch.billingsystem.repository.TransactionLogRepository
import ru.denisqq.asyncarch.billingsystem.service.TaskService
import ru.denisqq.asyncarch.billingsystem.service.TransactionService
import ru.denisqq.asyncarch.billingsystem.service.UserService

@Service
class TransactionServiceImpl(
    private val userService: UserService,
    private val taskService: TaskService,
    private val transactionLogRepository: TransactionLogRepository
) : TransactionService {
    @Transactional
    override fun creditTransaction(
        idempotenceKey: String,
        userSsoId: String,
        taskIntegrationId: String,
        details: String
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
        details: String
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
    }
}