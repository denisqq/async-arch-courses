package ru.denisqq.asyncarch.auth.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import jakarta.annotation.PostConstruct
import org.apache.avro.specific.SpecificRecord
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Service
import ru.denisqq.asyncarch.auth.config.TopicProperties
import ru.denisqq.asyncarch.auth.model.SsoUserDetails
import ru.denisqq.asyncarch.auth.service.RegistrationService
import ru.denisqq.asyncarch.auth.web.RegistrationController.RegistrationRequest
import ru.denisqq.asyncarch.authserver.UserRegistered
import java.util.*
import java.util.UUID.randomUUID

@Service
class RegistrationServiceImpl(
    @Qualifier("users")
    private val detailsManager: UserDetailsManager,
    private val passwordEncoder: PasswordEncoder,
    private val kafkaTemplate: KafkaTemplate<String, SpecificRecord>,
    private val objectMapper: ObjectMapper,
    private val topicProperties: TopicProperties,
) : RegistrationService {
    override fun register(request: RegistrationRequest): UserDetails {
        val user = SsoUserDetails(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            authorities = mutableListOf(SimpleGrantedAuthority("ROLE_${request.role}")),
            userSsoId = randomUUID()
        )

        detailsManager.createUser(user)

        kafkaTemplate.send(
            ProducerRecord(
                topicProperties.usersLifecycle,
                user.username,
                UserRegistered.newBuilder()
                    .setEventId(UUID.randomUUID().toString())
                    .setUsername(user.username)
                    .setRole(request.role)
                    .setUserSsoId(user.userSsoId.toString())
                    .build()
            )
        ).get()

        return user
    }

    @PostConstruct
    fun loadUsers() {
        val usersJson = this::class.java.classLoader.getResourceAsStream("users.json")
        val users = objectMapper.readValue<List<RegistrationRequest>>(usersJson!!.readAllBytes())
        users.forEach { register(it) }
    }


    data class UserRegisteredEvent(
        val eventName: String = "UserRegisteredEvent",
        val username: String,
        val userSsoId: String,
        val role: String,
    )
}