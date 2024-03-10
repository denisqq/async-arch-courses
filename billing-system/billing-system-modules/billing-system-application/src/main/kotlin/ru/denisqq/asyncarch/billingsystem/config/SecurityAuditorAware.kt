package ru.denisqq.asyncarch.billingsystem.config

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class SecurityAuditorAware: AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty()
        }

        return Optional.empty()
    }
}