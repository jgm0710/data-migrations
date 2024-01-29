package com.example.datamigrations.domain

import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

object DatasourceFactory {

    fun createDatasource(
        jdbcUrl: String,
        username: String,
        password: String,
        poolName: String,
    ): DataSource {
        val hikariDataSource = HikariDataSource()
        hikariDataSource.driverClassName = "org.postgresql.Driver"
        hikariDataSource.username = username
        hikariDataSource.password = password
        hikariDataSource.jdbcUrl = jdbcUrl
        hikariDataSource.poolName = poolName

        return hikariDataSource
    }

    fun createDatasource(connectionSetting: ConnectionSetting): DataSource {
        return createDatasource(
            jdbcUrl = connectionSetting.jdbcUrl,
            username = connectionSetting.username,
            password = connectionSetting.password,
            poolName = connectionSetting.poolName,
        )
    }
}
