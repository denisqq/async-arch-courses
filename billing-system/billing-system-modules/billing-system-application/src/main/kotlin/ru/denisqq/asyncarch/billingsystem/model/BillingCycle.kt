package ru.denisqq.asyncarch.billingsystem.model

import jakarta.persistence.Entity

@Entity
class BillingCycle(
    val name: String,
    val macros: String
): AbstractEntity()

const val DAILY_BILLING_CYCLES_MACROS = "@daily"