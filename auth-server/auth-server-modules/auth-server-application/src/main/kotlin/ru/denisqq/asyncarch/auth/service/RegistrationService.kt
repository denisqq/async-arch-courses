package ru.denisqq.asyncarch.auth.service

import org.springframework.security.core.userdetails.UserDetails
import ru.denisqq.asyncarch.auth.web.RegistrationController.RegistrationRequest


interface RegistrationService {
    fun register(request: RegistrationRequest): UserDetails
}