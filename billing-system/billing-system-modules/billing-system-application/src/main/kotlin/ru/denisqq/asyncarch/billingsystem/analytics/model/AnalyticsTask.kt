package ru.denisqq.asyncarch.billingsystem.analytics.model

import jakarta.persistence.*
import ru.denisqq.asyncarch.billingsystem.model.AbstractEntity
import ru.denisqq.asyncarch.billingsystem.model.TaskStatus
import java.math.BigDecimal

@Entity
@Table(
    indexes = [Index(columnList = "integration_id", unique = true)]
)
class AnalyticsTask(

    val integrationId: String,

    val title: String,

    val jiraId: String? = null,

    val description: String,

    @OneToOne
    @JoinColumn(name = "assign_id", referencedColumnName = "id", updatable = false)
    var assign: AnalyticsUser,

    @Enumerated(value = EnumType.STRING)
    var taskStatus: TaskStatus,

    val completeTaskCost: BigDecimal,

    val assignTaskCost: BigDecimal,
): AbstractEntity()