package com.mooncloak.kodetools.locale.storage.postgresql

import com.mooncloak.kodetools.locale.storage.*
import com.mooncloak.kodetools.locale.storage.postgresql.source.CountrySqliteSource
import com.mooncloak.kodetools.locale.storage.postgresql.source.RegionSqliteSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.Clock

public fun StorageProvider.Companion.postgresql(
    username: () -> String,
    passphrase: () -> String,
    url: () -> String,
    driverFactory: SqlDriverFactory = SqlDriverFactory.hikari(),
    clock: Clock = Clock.System,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): StorageProvider = PostgresqlStorageProvider(
    databaseProvider = DatabaseProvider(
        driverFactory = driverFactory,
        username = username,
        passphrase = passphrase,
        url = url
    ),
    clock = clock,
    dispatcher = dispatcher
)

internal class PostgresqlStorageProvider internal constructor(
    private val databaseProvider: DatabaseProvider,
    private val clock: Clock = Clock.System,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : StorageProvider {

    override val mutableCountryRepository: MutableCountryRepository
        get() = getCountryRepository()

    override val mutableRegionRepository: MutableRegionRepository
        get() = getRegionRepository()

    private var cachedCountryRepository: MutableCountryRepository? = null
    private var cachedRegionRepository: MutableRegionRepository? = null

    override fun close() {
        databaseProvider.close()
    }

    private fun getCountryRepository(): MutableCountryRepository {
        var repository = cachedCountryRepository

        if (repository == null) {
            repository = CountrySqliteSource(
                databaseProvider = databaseProvider,
                clock = clock,
                dispatcher = dispatcher
            )
        }

        return repository
    }

    private fun getRegionRepository(): MutableRegionRepository {
        var repository = cachedRegionRepository

        if (repository == null) {
            repository = RegionSqliteSource(
                databaseProvider = databaseProvider,
                clock = clock,
                dispatcher = dispatcher
            )
        }

        return repository
    }
}
