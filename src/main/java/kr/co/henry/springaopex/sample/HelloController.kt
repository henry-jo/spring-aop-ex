package kr.co.henry.springaopex.sample

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class HelloController {

    @GetMapping("/hello")
    fun hello(): String {
        return "HI"
    }
}