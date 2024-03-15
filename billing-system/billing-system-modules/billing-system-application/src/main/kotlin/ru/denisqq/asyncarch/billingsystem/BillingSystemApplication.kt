package ru.denisqq.asyncarch.billingsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.denisqq.asyncarch.billingsystem.config.KafkaTopicProperties

@SpringBootApplication
@EnableConfigurationProperties(KafkaTopicProperties::class)
class BillingSystemApplication

fun main(args: Array<String>) {
    runApplication<BillingSystemApplication>(*args)
}
