package ru.denisqq.asyncarch.billingsystem.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.support.DatabaseStartupValidator.DEFAULT_INTERVAL
import org.springframework.kafka.listener.*
import org.springframework.kafka.support.serializer.DeserializationException
import org.springframework.messaging.handler.invocation.MethodArgumentResolutionException
import org.springframework.util.backoff.ExponentialBackOff
import org.springframework.util.backoff.FixedBackOff
import java.net.SocketTimeoutException
import java.sql.SQLIntegrityConstraintViolationException
import java.sql.SQLTimeoutException
import kotlin.reflect.KClass

@Configuration
class KafkaConfig {

    @Bean
    fun errorHandler() = DefaultErrorHandler(
        ExponentialBackOff()
    ).also {

        /*
        * Классификация того, какие ошибки мы будем реатраить
        * */
        it.setClassifications(
            mapOf(
                SocketTimeoutException::class.java to true,
                SQLTimeoutException::class.java to true,


                SQLIntegrityConstraintViolationException::class.java to true, // Ошибка в случае, если есть запись с уникальным индексом
                DeserializationException::class.java to false,
                MethodArgumentResolutionException::class.java to false,
                NoSuchMethodException::class.java to false,
                ClassCastException::class.java to false
            ), true
        )
    }

}