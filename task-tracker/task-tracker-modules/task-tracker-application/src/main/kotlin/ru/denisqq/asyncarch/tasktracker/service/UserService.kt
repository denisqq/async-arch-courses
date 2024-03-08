package ru.denisqq.asyncarch.tasktracker.service

import ru.denisqq.asyncarch.tasktracker.kafka.UsersBeConsumer
import ru.denisqq.asyncarch.tasktracker.kafka.UsersBeConsumer.UserRegisteredEvent
import ru.denisqq.asyncarch.tasktracker.model.User

interface UserService {

    fun createUser(userRegisteredEvent: UserRegisteredEvent)

    fun findUsersByRole(role: String): List<User>

    fun findDevs(): List<User>
}