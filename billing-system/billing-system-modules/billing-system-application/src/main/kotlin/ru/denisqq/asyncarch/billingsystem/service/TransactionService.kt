package ru.denisqq.asyncarch.billingsystem.service

interface TransactionService {

    fun creditTransaction(
        idempotenceKey: String,
        userSsoId: String,
        taskIntegrationId: String,
        details: String
    )

    fun debitTransaction(
        idempotenceKey: String,
        userSsoId: String,
        taskIntegrationId: String,
        details: String
    )
}