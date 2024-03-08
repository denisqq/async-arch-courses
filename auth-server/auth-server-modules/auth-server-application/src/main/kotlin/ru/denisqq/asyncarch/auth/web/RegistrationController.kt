package ru.denisqq.asyncarch.auth.web

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.denisqq.asyncarch.auth.service.RegistrationService

@RestController
class RegistrationController(
    private val registrationService: RegistrationService
) {

    @PostMapping("/v1/sign-up")
    fun register(@RequestBody request: RegistrationRequest): ResponseEntity<Nothing> {
        registrationService.register(request)
        return ResponseEntity.noContent().build()
    }

    data class RegistrationRequest(
        val username: String,
        val password: String,
        val role: String
    )
}