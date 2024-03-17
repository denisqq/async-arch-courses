package ru.denisqq.asyncarch.billingsystem.kafka.error

import org.springframework.kafka.listener.KafkaListenerErrorHandler
import org.springframework.kafka.listener.ListenerExecutionFailedException
import org.springframework.messaging.Message
import org.springframework.stereotype.Component
import ru.denisqq.asyncarch.billingsystem.service.DeadLettersService

@Component
class BillingErrorHandler(
    private val deadLettersService: DeadLettersService
): KafkaListenerErrorHandler {
    override fun handleError(message: Message<*>, exception: ListenerExecutionFailedException): Any {
        val cause = exception.cause
        val payload = message.payload
        when (cause) {
            /*  Все рантайм ошибки сохраним в отдельную таблицу, например:
                java.lang.IllegalArgumentException
                java.lang.NullPointerException
             */
            is RuntimeException -> deadLettersService.create(payload)
        }

        return Unit
    }
}