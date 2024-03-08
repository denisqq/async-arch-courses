package ru.denisqq.asyncarch.tasktracker.service.impl

import org.springframework.stereotype.Service
import ru.denisqq.asyncarch.tasktracker.kafka.UsersBeConsumer
import ru.denisqq.asyncarch.tasktracker.model.User
import ru.denisqq.asyncarch.tasktracker.repository.UserRepository
import ru.denisqq.asyncarch.tasktracker.service.UserService

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {
    override fun createUser(userRegisteredEvent: UsersBeConsumer.UserRegisteredEvent) {
        userRepository.save(
            User(
                name = userRegisteredEvent.username,
                role = userRegisteredEvent.role,
                userSsoId = userRegisteredEvent.userSsoId
            )
        )
    }

    override fun findUsersByRole(role: String): List<User> {
        return userRepository.findUsersByRole(role)
    }

    override fun findDevs(): List<User> {
        //TODO вынести в enum
        return findUsersByRole("dev")
    }
}