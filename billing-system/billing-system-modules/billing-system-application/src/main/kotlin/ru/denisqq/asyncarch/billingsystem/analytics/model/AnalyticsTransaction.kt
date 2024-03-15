package ru.denisqq.asyncarch.billingsystem.analytics.model

import jakarta.persistence.*
import ru.denisqq.asyncarch.billingsystem.model.AbstractEntity
import ru.denisqq.asyncarch.billingsystem.model.BillingCycle
import ru.denisqq.asyncarch.billingsystem.model.Task
import ru.denisqq.asyncarch.billingsystem.model.User
import java.math.BigDecimal
import java.time.Instant
import java.util.*

@Entity
@Table(
    indexes = [Index(columnList = "integration_id", unique = true)]
)
class AnalyticsTransaction(
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    val user: AnalyticsUser,

    val credit: BigDecimal? = null,

    val debit: BigDecimal? = null,

    val details: String,

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", updatable = false)
    val task: AnalyticsTask,

    val integrationId: String,

    val transactionProcessedAt: Instant
) : AbstractEntity()