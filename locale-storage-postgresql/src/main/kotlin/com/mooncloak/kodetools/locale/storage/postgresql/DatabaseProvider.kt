package com.mooncloak.kodetools.locale.storage.postgresql

import app.cash.sqldelight.db.SqlDriver
import com.mooncloak.kodetools.locale.storage.postgresql.adapter.InstantAsOffsetDateTimeDatabaseAdapter

internal class DatabaseProvider internal constructor(
    private val driverFactory: SqlDriverFactory,
    private val username: () -> String,
    private val passphrase: () -> String,
    private val url: () -> String
) : AutoCloseable {

    private var cachedDriver: SqlDriver? = null
    private var cachedDatabase: LocaleDatabase? = null

    override fun close() {
        cachedDriver?.close()
        cachedDriver = null
        cachedDatabase = null
    }

    fun get(): LocaleDatabase {
        var database = cachedDatabase

        if (database == null) {
            val driver = driverFactory.create(
                username = username(),
                passphrase = passphrase(),
                url = url()
            )
            cachedDriver = driver

            database = createDatabase(driver)
            cachedDatabase = database
        }

        return database
    }

    private fun createDatabase(driver: SqlDriver) =
        LocaleDatabase(
            driver = driver,
            countryAdapter = Country.Adapter(
                createdAdapter = InstantAsOffsetDateTimeDatabaseAdapter,
                updatedAdapter = InstantAsOffsetDateTimeDatabaseAdapter
            ),
            regionAdapter = Region.Adapter(
                createdAdapter = InstantAsOffsetDateTimeDatabaseAdapter,
                updatedAdapter = InstantAsOffsetDateTimeDatabaseAdapter
            )
        )
}
