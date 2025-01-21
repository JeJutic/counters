package com.panart.counters

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CountersApplication

fun main(args: Array<String>) {
	runApplication<CountersApplication>(*args)
}
