package com.example.datamigrations.domain

data class ConnectionSetting(
    val jdbcUrl: String,
    val username: String,
    val password: String,
    val poolName: String,
)
