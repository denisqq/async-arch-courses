package ru.denisqq.asyncarch.billingsystem.analytics.service.impl

import org.springframework.stereotype.Service
import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsTransaction
import ru.denisqq.asyncarch.billingsystem.analytics.repository.AnalyticsTransactionRepository
import ru.denisqq.asyncarch.billingsystem.analytics.service.AnalyticsTransactionService
import ru.denisqq.asyncarch.tasktracker.TransactionCreated

@Service
class AnalyticsTransactionServiceImpl(
    private val analyticsTransactionRepository: AnalyticsTransactionRepository
) : AnalyticsTransactionService {
    override fun create(transactionCreated: TransactionCreated): AnalyticsTransaction {
        TODO()
    }
}