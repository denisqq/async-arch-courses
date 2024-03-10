package ru.denisqq.asyncarch.billingsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.denisqq.asyncarch.billingsystem.model.BillingCycle
import java.util.*

interface BillingCycleRepository: JpaRepository<BillingCycle, UUID> {
    fun findByMacros(macros: String): BillingCycle
}