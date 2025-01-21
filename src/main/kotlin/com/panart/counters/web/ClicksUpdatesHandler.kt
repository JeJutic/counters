package com.panart.counters.web

import com.fasterxml.jackson.databind.ObjectMapper
import com.panart.counters.service.ClicksService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.asPublisher
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class ClicksUpdatesHandler(
    private val clicksService: ClicksService,
    private val objectMapper: ObjectMapper
) : WebSocketHandler {

    override fun handle(session: WebSocketSession): Mono<Void> {
        return session.send(
            clicksService
                .infoFlow
                .map { objectMapper.writeValueAsString(it) }
                .map { session.textMessage(it) }
                .asPublisher()
        )
    }

}
