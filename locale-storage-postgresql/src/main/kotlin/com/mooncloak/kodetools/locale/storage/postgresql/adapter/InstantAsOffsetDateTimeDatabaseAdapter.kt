package com.mooncloak.kodetools.locale.storage.postgresql.adapter

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.OffsetDateTime
import java.time.ZoneOffset

/**
 * A [ColumnAdapter] implementation that stores [Instant] values as [OffsetDateTime] values, at the [ZoneOffset.UTC],
 * in the database.
 */
public object InstantAsOffsetDateTimeDatabaseAdapter : ColumnAdapter<Instant, OffsetDateTime> {

    override fun decode(databaseValue: OffsetDateTime): Instant =
        databaseValue.toInstant().toKotlinInstant()

    override fun encode(value: Instant): OffsetDateTime =
        value.toJavaInstant().atOffset(ZoneOffset.UTC)
}
