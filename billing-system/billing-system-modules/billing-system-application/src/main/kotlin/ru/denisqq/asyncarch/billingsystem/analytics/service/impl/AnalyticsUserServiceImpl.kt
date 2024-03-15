package ru.denisqq.asyncarch.billingsystem.analytics.service.impl

import org.springframework.stereotype.Service
import ru.denisqq.asyncarch.authserver.UserRegistered
import ru.denisqq.asyncarch.billingsystem.analytics.model.AnalyticsUser
import ru.denisqq.asyncarch.billingsystem.analytics.service.AnalyticsUserService

@Service
class AnalyticsUserServiceImpl : AnalyticsUserService {
    override fun create(userRegistered: UserRegistered): AnalyticsUser {
        TODO("Not yet implemented")
    }
}