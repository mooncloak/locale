package com.mooncloak.kodetools.locale.storage.sqlite.adapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.Instant

/**
 * A [ColumnAdapter] implementation that stores [Instant] values as millisecond [Long] values in the database.
 */
public object InstantAsMillisecondsLongDatabaseAdapter : ColumnAdapter<Instant, Long> {

    override fun decode(databaseValue: Long): Instant =
        Instant.fromEpochMilliseconds(databaseValue)

    override fun encode(value: Instant): Long =
        value.toEpochMilliseconds()
}
