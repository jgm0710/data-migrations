package com.example.datamigrations.application

import com.example.datamigrations.domain.DatasourceFactory
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Service
import java.sql.ResultSet

@Service
class MigrationAplService {

    fun sample() {
        val datasource = DatasourceFactory.createDatasource(
            jdbcUrl = "jdbc:postgresql://localhost:5434/settlement_service",
            username = "root",
            password = "passorder",
            poolName = "settlment-db",
        )

        val jdbcTemplate = NamedParameterJdbcTemplate(datasource)

        val result: MutableList<Map<String, Any?>> = jdbcTemplate.query(
            "select * from order_settlement limit 10;",
        ) { rs: ResultSet, rowNum ->
            val id: Any? = rs.getObject("id")
            val orderId: Any? = rs.getObject("order_id")
            val status: Any? = rs.getObject("status")
            mapOf(
                "id" to id,
                "orderId" to orderId,
                "status" to status,
            )
        }

        for (map in result) {
            println(map)
        }
    }
}
