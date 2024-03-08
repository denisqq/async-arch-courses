package ru.denisqq.asyncarch.auth.authserver

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.session.SessionRegistry
import org.springframework.security.core.session.SessionRegistryImpl
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.session.HttpSessionEventPublisher


@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
class DefaultSecurityConfig {

    @Bean
    fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf {
            it.disable()
        }.authorizeHttpRequests { authorize ->
            authorize.requestMatchers(
                "/login",
                "/v1/sign-up",
//                    "/error**",
//                    "/static/**",
//                    "/client/**",
//                    "/reset-password/**"
            ).permitAll()
                .anyRequest().authenticated()
        }
            .formLogin(withDefaults())
////            .oauth2Login {
//                it
//                    .loginPage("/login")
//                    .successHandler(authenticationSuccessHandler())
//            }

//            .formLogin(Customizer.withDefaults());

        return http.build()
    }

    @Bean
    fun users(objectMapper: ObjectMapper): UserDetailsManager {
        return InMemoryUserDetailsManager()
    }

    // @formatter:on
    @Bean
    fun sessionRegistry(): SessionRegistry {
        return SessionRegistryImpl()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun httpSessionEventPublisher(): HttpSessionEventPublisher {
        return HttpSessionEventPublisher()
    }
}