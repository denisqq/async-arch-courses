package ru.denisqq.asyncarch.tasktracker.model

import jakarta.persistence.*
import java.util.*

@Entity
class Task(
    var title: String,
    var description: String,
    @OneToOne
    @JoinColumn(name = "assign_id", referencedColumnName = "id", updatable = false)
    var assign: User,
    @Enumerated(value = EnumType.STRING)
    var taskStatus: TaskStatus
) : AbstractEntity() {
}