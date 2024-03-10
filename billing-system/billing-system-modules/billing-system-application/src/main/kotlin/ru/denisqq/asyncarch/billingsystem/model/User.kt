package ru.denisqq.asyncarch.billingsystem.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(
    name = "`user`",
    indexes = [Index(name = "user_user_sso_id_uniq_idx", unique = true, columnList = "user_sso_id")]
)
class User(
//    val name: String,
    val role: String,

    val userSsoId: String,

    @ManyToOne
    @JoinColumn(name = "billing_cycle_id", referencedColumnName = "id", updatable = false)
    val billingCycle: BillingCycle,

    val balance: BigDecimal
) : AbstractEntity()