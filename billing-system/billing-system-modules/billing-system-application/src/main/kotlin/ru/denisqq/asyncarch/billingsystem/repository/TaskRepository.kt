package ru.denisqq.asyncarch.billingsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.denisqq.asyncarch.billingsystem.model.Task
import java.util.*

interface TaskRepository: JpaRepository<Task, UUID> {

    fun findTaskByIntegrationId(integrationId: String): Task?

    fun existsTaskByIntegrationId(integrationId: String): Boolean
}