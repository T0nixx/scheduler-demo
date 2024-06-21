package com.example.schedulerdemo.client.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient
import java.time.LocalDate
import java.time.LocalDateTime

@Configuration
class RestClientConfig {

    @Bean
    fun restClient(): RestClient {
        return RestClient.builder().build()
    }
}

fun getPublishedAt(time: String): LocalDateTime {
    val (hour, minute) = time.split(":").let { it[0].toInt() to it[1].toInt() }
    val now = LocalDate.now()
    return now.atTime(hour, minute)
}
