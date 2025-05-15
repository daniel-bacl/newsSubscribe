package com.newssubscribe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NewsSubscribeApplication

fun main(args: Array<String>) {
    runApplication<NewsSubscribeApplication>(*args)
}