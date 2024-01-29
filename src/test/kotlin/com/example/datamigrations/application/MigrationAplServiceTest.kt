package com.example.datamigrations.application

import com.example.datamigrations.domain.ConnectionSetting
import com.example.datamigrations.domain.DataMap
import com.example.datamigrations.domain.DataMigrationSetting
import org.junit.jupiter.api.Test
import java.time.OffsetDateTime
import java.util.UUID

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
                    invokeFixedValue = { null },
                ),
                DataMap(
                    fromKey = "order_id",
                    toKey = "orderId",
                    invokeFixedValue = { null },
                ),
                DataMap(
                    fromKey = "status",
                    toKey = "status",
                    invokeFixedValue = { null },
                ),
            ),
        )

        val to = DataMigrationSetting(
            query = "insert into test_order_settlement (identifier, order_settlement_id, order_id, status, created_date)\n" +
                "values (:identifier, :order_settlement_id, :order_id, :status, :created_date);",
            dataMaps = listOf(
                DataMap(
                    fromKey = null,
                    toKey = "identifier",
                    invokeFixedValue = { UUID.randomUUID() },
                ),
                DataMap(
                    fromKey = "identifier",
                    toKey = "order_settlement_id",
                    invokeFixedValue = { null },
                ),
                DataMap(
                    fromKey = "orderId",
                    toKey = "order_id",
                    invokeFixedValue = { null },
                ),
                DataMap(
                    fromKey = "status",
                    toKey = "status",
                    invokeFixedValue = { null },
                ),
                DataMap(
                    fromKey = null,
                    toKey = "created_date",
                    invokeFixedValue = { OffsetDateTime.now() },
                ),
            ),
        )

        dataMigrator.migrate(
            from,
            to,
        )
    }
}
