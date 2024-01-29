package com.example.datamigrations.domain

data class DataMigrationSetting(
    val query: String,
    val dataMaps: Set<DataMap>,
)
