package ru.denisqq.asyncarch.billingsystem.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    indexes = [Index(columnList = "idempotence_key, task_id", unique = true)]
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

    val billingCycleClosed: Boolean = false
): AbstractEntity() {
}