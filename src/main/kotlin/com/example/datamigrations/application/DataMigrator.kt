package com.example.datamigrations.application

import com.example.datamigrations.domain.ConnectionSetting
import com.example.datamigrations.domain.DataMap
import com.example.datamigrations.domain.DataMigrationSetting
import com.example.datamigrations.domain.DatasourceFactory
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import java.sql.ResultSet

class DataMigrator(
    fromCollectionSetting: ConnectionSetting,
    toCollectionSetting: ConnectionSetting,
) {

    private val fromDataSource = DatasourceFactory.createDatasource(fromCollectionSetting)
    private val toDataSource = DatasourceFactory.createDatasource(toCollectionSetting)

    fun migrate(
        from: DataMigrationSetting,
        to: DataMigrationSetting,
    ) {
        try {
            val fromJdbcTemplate = NamedParameterJdbcTemplate(fromDataSource)

            val resultMaps: MutableList<Map<String, Any?>> = fromJdbcTemplate.query(
                from.query,
            ) { rs: ResultSet, rowNum ->
                val resultMap = mutableMapOf<String, Any>()
                for (dataMap in from.dataMaps) {
                    val any = rs.getObject(dataMap.fromKey)
                    val toKey = dataMap.toKey
                    if (toKey != null) {
                        resultMap[toKey] = any
                    }
                }
                resultMap
            }

            val parameterSources = resultMaps.map { resultMap ->
                val mapSqlParameterSource = MapSqlParameterSource()

                for (dataMap: DataMap in to.dataMaps) {
                    val fromKey: String? = dataMap.fromKey
                    val toKey: String? = dataMap.toKey
                    val fixedValue: Any? = dataMap.invokeFixedValue

                    if (toKey == null) {
                        continue
                    }

                    if (fromKey != null) {
                        mapSqlParameterSource.addValue(toKey, resultMap[fromKey])
                    } else {
                        mapSqlParameterSource.addValue(toKey, fixedValue)
                    }
                }

                mapSqlParameterSource
            }.toTypedArray()

            val toJdbcTemplate = NamedParameterJdbcTemplate(toDataSource)

            toJdbcTemplate.batchUpdate(
                to.query,
                parameterSources,
            )
        } finally {
            Thread.sleep(1000)
            fromDataSource.connection.close()
            toDataSource.connection.close()
            Thread.sleep(1000)
        }
    }
}
