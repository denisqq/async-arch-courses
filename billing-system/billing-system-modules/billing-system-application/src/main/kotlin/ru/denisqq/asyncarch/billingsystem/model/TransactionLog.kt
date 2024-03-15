package ru.denisqq.asyncarch.billingsystem.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(
    indexes = [
        Index(columnList = "integration_id", unique = true),
        Index(columnList = "idempotence_key, task_id", unique = true),
    ]
)
class TransactionLog(
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false)
    val user: User,

    val credit: BigDecimal? = null,

    val debit: BigDecimal? = null,

    val details: String,

    @ManyToOne
    @JoinColumn(name = "task_id", referencedColumnName = "id", updatable = false)
    val task: Task,

    @ManyToOne
    @JoinColumn(name = "billing_cycle_id", referencedColumnName = "id", updatable = false)
    val billingCycle: BillingCycle,

    val idempotenceKey: String,

    val billingCycleClosed: Boolean = false,

    val integrationId: String = UUID.randomUUID().toString(),
): AbstractEntity() {
}