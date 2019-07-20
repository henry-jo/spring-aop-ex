package kr.co.henry.springaopex.retryable

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Service

@Service
class RetryableService {

    private val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @Retryable(include = [RuntimeException::class], backoff = Backoff(1000), maxAttempts = 3)
    fun retry() {

        log.info("!!")

        throw RuntimeException()
    }
}