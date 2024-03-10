package ru.denisqq.asyncarch.tasktracker.model

import jakarta.persistence.*

@Entity
class Task(
    val integrationId: String,
    val title: String,
    val jiraId: String,
    val description: String,
    @OneToOne
    @JoinColumn(name = "assign_id", referencedColumnName = "id", updatable = false)
    var assign: User,
    @Enumerated(value = EnumType.STRING)
    var taskStatus: TaskStatus
) : AbstractEntity() {
}