package ru.denisqq.asyncarch.tasktracker.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.denisqq.asyncarch.authserver.UserRegistered
import ru.denisqq.asyncarch.tasktracker.service.UserService

@Component
class UsersConsumer(
    private val userService: UserService
) {

    @KafkaListener(topics = ["\${spring.kafka.topics.users-registered}"])
    fun usersRegisteredConsume(@Payload userRegistered: UserRegistered) {
        userService.createUser(userRegistered)
    }

}