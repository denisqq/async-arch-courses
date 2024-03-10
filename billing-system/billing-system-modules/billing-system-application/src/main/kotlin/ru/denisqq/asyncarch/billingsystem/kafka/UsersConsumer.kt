package ru.denisqq.asyncarch.billingsystem.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.denisqq.asyncarch.authserver.UserRegistered
import ru.denisqq.asyncarch.billingsystem.service.UserService

@Component
class UsersConsumer(
    private val userService: UserService,
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.users-lifecycle}"],
//        errorHandler = "fixedAttemptsErrorHandler"
    )
    fun usersBusinessEvents(userRegisteredEvent: UserRegistered) {
        userService.createUser(userRegisteredEvent)
    }

}