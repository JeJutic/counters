package com.panart.counters.db

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory
import org.springframework.data.redis.core.ReactiveRedisOperations
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
class DatabaseConfiguration {

    @Bean
    fun redisOperations(factory: ReactiveRedisConnectionFactory):
            ReactiveRedisOperations<String, Long> {
        val longSerializer = GenericToStringSerializer(Long::class.java)

        return ReactiveRedisTemplate(
            factory,
            RedisSerializationContext
                .newSerializationContext<String, Long>(
                    StringRedisSerializer()
                )
                .value(longSerializer)
                .build()
        )
    }

}