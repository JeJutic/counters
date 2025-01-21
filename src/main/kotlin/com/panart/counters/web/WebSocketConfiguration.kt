package com.panart.counters.web

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping


@Configuration
class WebSocketConfiguration(val webSocketHandler: ClicksUpdatesHandler) {

    @Bean
    fun webSocketHandlerMapping(): HandlerMapping {
        return SimpleUrlHandlerMapping().apply {
            order = 1
            urlMap = mapOf(
                "/clicks/updates" to webSocketHandler
            )
        }
    }

}