package ru.denisqq.asyncarch.billingsystem.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
class SecurityConfig {
    @Bean
    //TODO пока оставим клиента от таск-трекера
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf {
            it.disable()
        }.authorizeHttpRequests { authorize ->
                authorize.requestMatchers("/oauth2/**", "/login/**").permitAll()
                    // TODO Сюда приходит OIDC_USER, SCOPE_openid
//                    .requestMatchers("/tasks/**").hasAuthority("SCOPE_task.read")
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer {
                it.jwt(withDefaults())
            }
            .oauth2Client(withDefaults())
            .oauth2Login {
                it.loginPage(
                    "/oauth2/authorization/billing-system-oidc"
                )
            }

        return http.build()
    }

    @Bean
    fun authorizedClientManager(
        clientRegistrationRepository: ClientRegistrationRepository,
        authorizedClientRepository: OAuth2AuthorizedClientRepository
    ): OAuth2AuthorizedClientManager {
        val authorizedClientProvider =
            OAuth2AuthorizedClientProviderBuilder.builder()
                .authorizationCode()
                .refreshToken()
                .build()
        val authorizedClientManager = DefaultOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientRepository
        )
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider)

        return authorizedClientManager
    }
}