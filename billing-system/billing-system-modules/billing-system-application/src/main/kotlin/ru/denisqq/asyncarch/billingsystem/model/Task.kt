package ru.denisqq.asyncarch.billingsystem.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class Task(
    val completeTaskCost: BigDecimal,
    val assignTaskCost: BigDecimal,
    val integrationId: String,
    @OneToOne
    @JoinColumn(name = "assign_id", referencedColumnName = "id", updatable = false)
    var assign: User,

    @Enumerated(value = EnumType.STRING)
    var taskStatus: TaskStatus
) : AbstractEntity()