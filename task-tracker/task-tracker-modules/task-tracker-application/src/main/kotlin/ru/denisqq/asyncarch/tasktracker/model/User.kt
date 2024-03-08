package ru.denisqq.asyncarch.tasktracker.model

import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    name = "`user`",
    indexes = [Index(name = "user_user_sso_id_uniq_idx", unique = true, columnList = "user_sso_id")]
)
class User(
    val name: String,
    val role: String,
    val userSsoId: String
) : AbstractEntity()