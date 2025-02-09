package com.mooncloak.kodetools.locale.storage.postgresql

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

public fun interface SqlDriverFactory {

    public fun create(
        username: String,
        passphrase: String,
        url: String
    ): SqlDriver

    public companion object
}

public fun SqlDriverFactory.Companion.hikari(
    create: Boolean = false,
    createConfig: () -> HikariConfig = { HikariConfig() }
): SqlDriverFactory = HikariSqlDriverFactory(
    create = create,
    createConfig = createConfig
)

internal class HikariSqlDriverFactory internal constructor(
    private val create: Boolean,
    private val createConfig: () -> HikariConfig
) : SqlDriverFactory {

    override fun create(username: String, passphrase: String, url: String): SqlDriver {
        val hikariConfig = createConfig().apply {
            this.jdbcUrl = url
            this.driverClassName = org.postgresql.Driver::class.java.getName()
            this.username = username
            this.password = passphrase
        }
        val dataSource = HikariDataSource(hikariConfig)

        val driver = dataSource.asJdbcDriver()

        if (create) {
            LocaleDatabase.Schema.create(driver)
        }

        return driver
    }
}
