package com.example.datamigrations

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DataMigrationsApplication

fun main(args: Array<String>) {
    runApplication<DataMigrationsApplication>(*args)
}
