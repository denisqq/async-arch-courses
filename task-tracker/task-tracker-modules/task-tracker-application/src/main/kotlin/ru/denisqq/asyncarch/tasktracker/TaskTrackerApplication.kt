package ru.denisqq.asyncarch.tasktracker

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.denisqq.asyncarch.tasktracker.config.TopicsProperties

@SpringBootApplication
@EnableConfigurationProperties(TopicsProperties::class)
class TaskTrackerApplication

fun main(args: Array<String>) {
    runApplication<TaskTrackerApplication>(*args)
}
