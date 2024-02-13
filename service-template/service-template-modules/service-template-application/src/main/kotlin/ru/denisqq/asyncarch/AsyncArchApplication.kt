package ru.denisqq.asyncarch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AsyncArchApplication

fun main(args: Array<String>) {
    runApplication<AsyncArchApplication>(*args)
}
