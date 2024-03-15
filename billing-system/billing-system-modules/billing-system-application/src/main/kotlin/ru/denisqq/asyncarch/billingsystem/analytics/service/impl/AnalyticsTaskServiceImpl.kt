package ru.denisqq.asyncarch.billingsystem.analytics.service.impl

import org.springframework.stereotype.Service
import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsTask
import ru.denisqq.asyncarch.billingsystem.analytics.service.AnalyticsTaskService
import ru.denisqq.asyncarch.tasktracker.TaskChanged
import ru.denisqq.asyncarch.tasktracker.TaskPriceChanged

@Service
class AnalyticsTaskServiceImpl : AnalyticsTaskService {
    override fun createOrUpdate(taskChanged: TaskChanged): AnalyticsTask {
        TODO("Not yet implemented")
    }

    override fun updatePrice(taskPriceChanged: TaskPriceChanged): AnalyticsTask {
        TODO("Not yet implemented")
    }
}