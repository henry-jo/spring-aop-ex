package kr.co.henry.springaopex.retryable

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/retry")
class RetryableController {

    @Autowired
    private lateinit var retryableService: RetryableService

    @GetMapping("/test")
    fun retryTest() {
        retryableService.retry()
    }
}