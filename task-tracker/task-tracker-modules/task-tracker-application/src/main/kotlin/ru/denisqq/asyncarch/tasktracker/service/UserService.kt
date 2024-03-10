package ru.denisqq.asyncarch.tasktracker.service

import ru.denisqq.asyncarch.authserver.UserRegistered
import ru.denisqq.asyncarch.tasktracker.model.User

interface UserService {

    fun createUser(userRegisteredEvent: UserRegistered)

    fun findUsersByRole(role: String): List<User>

    fun findDevs(): List<User>
}