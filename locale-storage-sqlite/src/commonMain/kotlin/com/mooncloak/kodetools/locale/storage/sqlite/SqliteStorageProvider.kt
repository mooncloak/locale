package com.mooncloak.kodetools.locale.storage.sqlite

import com.mooncloak.kodetools.locale.storage.*
import com.mooncloak.kodetools.locale.storage.sqlite.source.CountrySqliteSource
import com.mooncloak.kodetools.locale.storage.sqlite.source.RegionSqliteSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.Clock

public fun StorageProvider.Companion.sqlite(
    driverFactory: SqlDriverFactory,
    filePath: String? = null,
    clock: Clock = Clock.System,
    dispatcher: CoroutineDispatcher = Dispatchers.IO
): StorageProvider = SqliteStorageProvider(
    databaseProvider = DatabaseProvider(
        driverFactory = driverFactory,
        filePath = filePath
    ),
    clock = clock,
    dispatcher = dispatcher
)

internal class SqliteStorageProvider internal constructor(
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
