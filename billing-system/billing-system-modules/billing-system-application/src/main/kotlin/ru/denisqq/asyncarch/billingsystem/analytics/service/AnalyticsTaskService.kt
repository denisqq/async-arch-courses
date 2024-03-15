package ru.denisqq.asyncarch.billingsystem.analytics.service

import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsTask
import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsTransaction
import ru.denisqq.asyncarch.tasktracker.TaskChanged
import ru.denisqq.asyncarch.tasktracker.TaskPriceChanged
import ru.denisqq.asyncarch.tasktracker.TransactionCreated

interface AnalyticsTaskService {
    fun createOrUpdate(taskChanged: TaskChanged): AnalyticsTask

    fun updatePrice(taskPriceChanged: TaskPriceChanged): AnalyticsTask
}