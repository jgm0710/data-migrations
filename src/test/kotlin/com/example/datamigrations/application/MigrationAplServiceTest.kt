package com.example.datamigrations.application

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MigrationAplServiceTest {

    @Autowired
    lateinit var migrationAplService: MigrationAplService

    @Test
    fun name() {
        migrationAplService.sample()
    }
}
