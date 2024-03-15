package ru.denisqq.asyncarch.billingsystem.analytics.service

import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsTransaction
import ru.denisqq.asyncarch.tasktracker.TransactionCreated

interface AnalyticsTransactionService {
    fun create(transactionCreated: TransactionCreated): AnalyticsTransaction
}