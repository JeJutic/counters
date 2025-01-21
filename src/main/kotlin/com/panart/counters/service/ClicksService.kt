package com.panart.counters.service

import com.panart.counters.domain.ClicksInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.keysAsFlow
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

interface ClicksService {
    val infoFlow: StateFlow<List<ClicksInfo>>

    suspend fun addClick(id: Long)
}

@Service
class ClicksServiceImpl(
    private val redisOperations: ReactiveRedisOperations<String, Long>
) : ClicksService {
    private val infoFlowInner: MutableStateFlow<List<ClicksInfo>> =
        MutableStateFlow(emptyList())
    override val infoFlow: StateFlow<List<ClicksInfo>>
        get() = infoFlowInner

    override suspend fun addClick(id: Long) {
        // TODO check if id exists
        redisOperations.opsForValue()
            .increment(id.toString())
            .awaitLast()
    }

    @Scheduled(initialDelay = 0, fixedDelayString = "\${app.clicks-fetch-interval-ms}")
    private suspend fun infoUpdate() {
//        redisOperations.keys("*")
//            .flatMap { key ->
//                redisOperations.opsForValue().get(key)
//                    .map { ClicksInfo(key.toLong(), it) }
//            }.collectList()
//            .subscribe { infoFlowInner.value = it }

        val newInfo = redisOperations.keysAsFlow("*")
            .map { key ->
                redisOperations.opsForValue().get(key)
                    .map { ClicksInfo(key.toLong(), it) }
                    .awaitSingle()
            }.toList()
        infoFlowInner.value = newInfo
    }

}