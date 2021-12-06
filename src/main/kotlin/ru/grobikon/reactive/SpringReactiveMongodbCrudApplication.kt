package ru.grobikon.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringReactiveMongodbCrudApplication

fun main(args: Array<String>) {
    runApplication<SpringReactiveMongodbCrudApplication>(*args)
}
