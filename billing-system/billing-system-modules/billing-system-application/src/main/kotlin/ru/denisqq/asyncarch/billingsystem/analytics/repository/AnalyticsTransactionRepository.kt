package ru.denisqq.asyncarch.billingsystem.analytics.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsTransaction
import java.util.UUID

interface AnalyticsTransactionRepository: JpaRepository<AnalyticsTransaction, UUID> {
}