package ru.denisqq.asyncarch.billingsystem.service

import ru.denisqq.asyncarch.authserver.UserRegistered
import ru.denisqq.asyncarch.billingsystem.model.User
import java.math.BigDecimal

interface UserService {

    fun createUser(userRegisteredEvent: UserRegistered)

    fun findBySsoId(userSsoId: String): User

    fun updateBalance(userSsoId: String, balance: BigDecimal)
}