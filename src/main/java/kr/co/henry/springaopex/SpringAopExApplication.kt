package kr.co.henry.springaopex

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.retry.annotation.EnableRetry

@SpringBootApplication
@EnableRetry
class SpringAopExApplication

fun main(args: Array<String>) {
    SpringApplication.run(SpringAopExApplication::class.java, *args)
}
