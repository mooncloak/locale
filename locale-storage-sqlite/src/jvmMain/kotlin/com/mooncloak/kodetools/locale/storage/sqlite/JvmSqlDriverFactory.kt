package com.mooncloak.kodetools.locale.storage.sqlite

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver

public operator fun SqlDriverFactory.Companion.invoke(
    format: Boolean = true,
    create: Boolean = false
): SqlDriverFactory = JvmSqlDriverFactory(
    format = format,
    create = create
)

internal class JvmSqlDriverFactory internal constructor(
    private val format: Boolean,
    private val create: Boolean
) : SqlDriverFactory {

    override fun create(filePath: String?): SqlDriver {
        val url = when {
            !format -> filePath ?: JdbcSqliteDriver.IN_MEMORY
            filePath.isNullOrBlank() -> JdbcSqliteDriver.IN_MEMORY
            filePath.startsWith("jdbc:sqlite:") -> filePath
            else -> "jdbc:sqlite:$filePath"
        }

        val driver = JdbcSqliteDriver(url)

        if (create) {
            LocaleDatabase.Schema.create(driver)
        }

        return driver
    }
}
