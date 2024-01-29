package com.example.datamigrations.application

import com.example.datamigrations.domain.ConnectionSetting
import com.example.datamigrations.domain.DataMap
import com.example.datamigrations.domain.DataMigrationSetting
import org.junit.jupiter.api.Test

// @SpringBootTest
class MigrationAplServiceTest {

    val dataMigrator = DataMigrator(
        ConnectionSetting(
            jdbcUrl = "jdbc:postgresql://localhost:5434/settlement_service",
            username = "root",
            password = "passorder",
            poolName = "settlment-db",
        ),
        ConnectionSetting(
            jdbcUrl = "jdbc:postgresql://localhost:5434/settlement_service",
            username = "root",
            password = "passorder",
            poolName = "settlment-db2",
        ),
    )

    @Test
    fun name() {
        val from = DataMigrationSetting(
            query = "select * from order_settlement limit 10;",
            dataMaps = listOf(
                DataMap(
                    fromKey = "id",
                    toKey = "identifier",
                ),
                DataMap(
                    fromKey = "order_id",
                    toKey = "orderId",
                ),
                DataMap(
                    fromKey = "status",
                    toKey = "status",
                ),
            ),
        )

        dataMigrator.migrate(
            from,
            from,
        )
    }
}
