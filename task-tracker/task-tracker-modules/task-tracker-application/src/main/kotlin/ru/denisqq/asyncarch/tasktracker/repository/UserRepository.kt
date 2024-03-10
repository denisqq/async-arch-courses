package ru.denisqq.asyncarch.tasktracker.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.denisqq.asyncarch.tasktracker.model.User
import java.util.*

interface UserRepository: JpaRepository<User, UUID> {
    fun findUsersByRole(role: String): List<User>
}