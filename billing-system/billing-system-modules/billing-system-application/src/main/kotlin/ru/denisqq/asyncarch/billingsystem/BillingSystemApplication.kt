package ru.denisqq.asyncarch.billingsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
//@EnableConfigurationProperties(TopicsProperties::class)
class BillingSystemApplication

fun main(args: Array<String>) {
    runApplication<BillingSystemApplication>(*args)
}
