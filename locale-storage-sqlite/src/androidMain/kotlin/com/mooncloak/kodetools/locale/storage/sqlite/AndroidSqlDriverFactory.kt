package com.mooncloak.kodetools.locale.storage.sqlite

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

public operator fun SqlDriverFactory.Companion.invoke(
    context: Context,
    create: Boolean = false
): SqlDriverFactory = AndroidSqlDriverFactory(
    context = context,
    create = create
)

internal class AndroidSqlDriverFactory internal constructor(
    private val context: Context,
    private val create: Boolean
) : SqlDriverFactory {

    override fun create(filePath: String?): SqlDriver {
        val driver = AndroidSqliteDriver(LocaleDatabase.Schema, context, filePath)

        if (create) {
            LocaleDatabase.Schema.create(driver)
        }

        return driver
    }
}
