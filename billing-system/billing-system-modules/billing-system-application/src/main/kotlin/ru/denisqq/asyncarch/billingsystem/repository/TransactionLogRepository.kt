package ru.denisqq.asyncarch.billingsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.denisqq.asyncarch.billingsystem.model.TransactionLog
import java.util.*

interface TransactionLogRepository: JpaRepository<TransactionLog, UUID> {
}