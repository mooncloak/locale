package com.mooncloak.kodetools.locale.storage.sqlite

import app.cash.sqldelight.db.SqlDriver

public fun interface SqlDriverFactory {

    public fun create(filePath: String?): SqlDriver

    public companion object
}
