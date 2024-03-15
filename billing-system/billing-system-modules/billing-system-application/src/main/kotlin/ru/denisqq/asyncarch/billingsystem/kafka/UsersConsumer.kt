package ru.denisqq.asyncarch.billingsystem.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.denisqq.asyncarch.authserver.UserRegistered
import ru.denisqq.asyncarch.billingsystem.service.UserService

@Component
class UsersConsumer(
    private val userService: UserService,
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.users-registered}"],
//        errorHandler = "fixedAttemptsErrorHandler"
    )
    fun usersRegisteredConsume(@Payload userRegisteredEvent: UserRegistered) {
        userService.createUser(userRegisteredEvent)
    }

}