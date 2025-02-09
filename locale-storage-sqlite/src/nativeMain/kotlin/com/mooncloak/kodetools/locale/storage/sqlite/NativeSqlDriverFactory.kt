package com.mooncloak.kodetools.locale.storage.sqlite

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

public operator fun SqlDriverFactory.Companion.invoke(
    create: Boolean = false
): SqlDriverFactory = NativeSqlDriverFactory(
    create = create
)

internal class NativeSqlDriverFactory internal constructor(
    private val create: Boolean
) : SqlDriverFactory {

    override fun create(filePath: String?): SqlDriver {
        if (filePath == null) {
            error("'filePath' must not be `null` when creating a Native SqlDriver.")
        }

        val driver = NativeSqliteDriver(LocaleDatabase.Schema, filePath)

        if (create) {
            LocaleDatabase.Schema.create(driver)
        }

        return driver
    }
}
