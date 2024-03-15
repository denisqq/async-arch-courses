package ru.denisqq.asyncarch.billingsystem.analytics.service

import ru.denisqq.asyncarch.authserver.UserRegistered
import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsTask
import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsTransaction
import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsUser
import ru.denisqq.asyncarch.tasktracker.TaskChanged
import ru.denisqq.asyncarch.tasktracker.TransactionCreated

interface AnalyticsUserService {
    fun create(userRegistered: UserRegistered): AnalyticsUser
}