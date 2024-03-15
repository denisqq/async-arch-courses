package ru.denisqq.asyncarch.billingsystem.analytics.model

import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table
import ru.denisqq.asyncarch.billingsystem.model.AbstractEntity

@Entity
@Table(
    indexes = [Index(columnList = "user_sso_id", unique = true)]
)
class AnalyticsUser(
    val name: String,

    val role: String,

    val userSsoId: String,
) : AbstractEntity()
