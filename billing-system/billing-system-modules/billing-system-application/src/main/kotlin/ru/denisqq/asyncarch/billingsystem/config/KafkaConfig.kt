package ru.denisqq.asyncarch.billingsystem.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.support.DatabaseStartupValidator.DEFAULT_INTERVAL
import org.springframework.kafka.listener.*
import org.springframework.util.backoff.ExponentialBackOff
import org.springframework.util.backoff.FixedBackOff

@Configuration
class KafkaConfig {

    @Bean
    fun backOffErrorHandler(): BackOffHandler {
        return DefaultBackOffHandler()
    }

//    @Bean
//    fun infiniteErrorHandler(): KafkaListenerErrorHandler = DefaultErrorHandler(
//        ExponentialBackOff()
//    )
//
//    @Bean
//    fun fixedAttemptsErrorHandler(): CommonErrorHandler = DefaultErrorHandler(
//        FixedBackOff(
//            DEFAULT_INTERVAL.toLong(), 5
//        )
//    )

}