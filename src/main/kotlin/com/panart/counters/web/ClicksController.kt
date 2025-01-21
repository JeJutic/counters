package com.panart.counters.web

import com.panart.counters.service.ClicksService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/clicks")
class ClicksController(val clicksService: ClicksService) {

    @PostMapping("/add/{id}")
    suspend fun addClick(@PathVariable("id") id: Long) {
        clicksService.addClick(id)
    }

}