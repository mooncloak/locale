package com.mooncloak.kodetools.locale.storage.sqlite

import app.cash.sqldelight.db.SqlDriver
import com.mooncloak.kodetools.locale.storage.sqlite.adapter.InstantAsMillisecondsLongDatabaseAdapter

internal class DatabaseProvider internal constructor(
    private val driverFactory: SqlDriverFactory,
    private val filePath: String? = null
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
            val driver = driverFactory.create(filePath = filePath)
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
                createdAdapter = InstantAsMillisecondsLongDatabaseAdapter,
                updatedAdapter = InstantAsMillisecondsLongDatabaseAdapter
            ),
            regionAdapter = Region.Adapter(
                createdAdapter = InstantAsMillisecondsLongDatabaseAdapter,
                updatedAdapter = InstantAsMillisecondsLongDatabaseAdapter
            )
        )
}
