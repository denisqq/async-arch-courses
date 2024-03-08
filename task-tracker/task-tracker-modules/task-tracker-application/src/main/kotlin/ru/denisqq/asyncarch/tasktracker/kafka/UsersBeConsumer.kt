package ru.denisqq.asyncarch.tasktracker.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import ru.denisqq.asyncarch.tasktracker.service.UserService
import java.sql.SQLException

@Component
class UsersBeConsumer(
    private val userService: UserService,
    private val objectMapper: ObjectMapper
) {

    //TODO десереализовать с помощью JsonMessageConverter
    @KafkaListener(topics = ["\${spring.kafka.topics.users-be}"])
    fun usersBusinessEvents(@Payload userRegisteredEvent: String) {
        val parsedEvent = objectMapper.readValue<UserRegisteredEvent>(userRegisteredEvent)
        userService.createUser(parsedEvent)
    }


    data class UserRegisteredEvent(
        val eventName: String = "UserRegisteredEvent",
        val username: String,
        val userSsoId: String,
        val role: String
    )
}