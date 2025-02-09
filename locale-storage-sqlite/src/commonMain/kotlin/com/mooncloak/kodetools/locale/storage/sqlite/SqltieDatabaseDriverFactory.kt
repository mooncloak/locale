package com.mooncloak.kodetools.locale.storage.sqlite

import app.cash.sqldelight.db.SqlDriver

public interface SqliteDatabaseDriverFactory {

    public fun create(filePath: String?): SqlDriver

    public companion object
}
