package com.example.datamigrations.domain

data class DataMap(
    val fromKey: String?,
    val toKey: String?,
    val invokeFixedValue: () -> Any? = { null },
)
