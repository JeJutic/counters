package com.panart.counters.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController {

    @GetMapping
    fun index(): String {
        return "index"
    }

}