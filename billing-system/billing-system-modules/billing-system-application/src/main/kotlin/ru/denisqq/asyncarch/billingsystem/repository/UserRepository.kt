package ru.denisqq.asyncarch.billingsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.denisqq.asyncarch.billingsystem.model.User
import java.math.BigDecimal
import java.util.*

interface UserRepository: JpaRepository<User, UUID> {
    fun findByUserSsoId(userSsoId: String): User

    @Query(
        "update User set balance = :balance where userSsoId = :userSsoId"
    )
    fun updateUserBalance(userSsoId: String, balance: BigDecimal)
}