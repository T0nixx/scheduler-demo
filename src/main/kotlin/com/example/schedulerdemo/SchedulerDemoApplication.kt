package com.example.schedulerdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class SchedulerDemoApplication

fun main(args: Array<String>) {
    runApplication<SchedulerDemoApplication>(*args)
}
