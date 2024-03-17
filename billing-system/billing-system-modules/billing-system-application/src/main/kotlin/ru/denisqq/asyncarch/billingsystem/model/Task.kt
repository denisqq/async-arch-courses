package ru.denisqq.asyncarch.billingsystem.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
class Task(
    val completeTaskCost: BigDecimal? = null,
    val assignTaskCost: BigDecimal? = null,
    val integrationId: String,
    @OneToOne
    @JoinColumn(name = "assign_id", referencedColumnName = "id", updatable = false)
    var assign: User? = null,

    @Enumerated(value = EnumType.STRING)
    var taskStatus: TaskStatus? = null
) : AbstractEntity()