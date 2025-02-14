package com.mooncloak.kodetools.locale.storage.sqlite.source

import com.mooncloak.kodetools.locale.CountryCode
import com.mooncloak.kodetools.locale.Region
import com.mooncloak.kodetools.locale.RegionCode
import com.mooncloak.kodetools.locale.storage.MutableRegionRepository
import com.mooncloak.kodetools.locale.storage.sqlite.DatabaseProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class RegionSqliteSource internal constructor(
    private val databaseProvider: DatabaseProvider,
    private val clock: Clock,
    private val dispatcher: CoroutineDispatcher
) : MutableRegionRepository {

    private val mutex = Mutex(locked = false)

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun add(region: Region): Region =
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                val now = clock.now()

                database.regionQueries.insert(
                    id = Uuid.random().toHexString(),
                    created = now,
                    updated = now,
                    code = region.code.value.uppercase(),
                    country_code = region.code.toCountryCode().value.uppercase(),
                    name = region.name,
                    flag = region.flag,
                    emoji_flag = region.emojiFlag,
                    type = region.type
                )

                return@withContext region
            }
        }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun addAll(regions: List<Region>) {
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                val now = clock.now()

                database.transaction {
                    regions.forEach { region ->
                        database.regionQueries.insert(
                            id = Uuid.random().toHexString(),
                            created = now,
                            updated = now,
                            code = region.code.value.uppercase(),
                            country_code = region.code.toCountryCode().value.uppercase(),
                            name = region.name,
                            flag = region.flag,
                            emoji_flag = region.emojiFlag,
                            type = region.type
                        )
                    }
                }
            }
        }
    }

    override suspend fun update(region: Region): Region =
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                val now = clock.now()

                database.regionQueries.updateAllByCode(
                    code = region.code.value.uppercase(),
                    updated = now,
                    name = region.name,
                    flag = region.flag,
                    emojiFlag = region.emojiFlag,
                    type = region.type
                )

                return@withContext region
            }
        }

    override suspend fun remove(code: RegionCode) {
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                database.regionQueries.deleteByCode(code.value.uppercase())
            }
        }
    }

    override suspend fun removeIn(codes: List<RegionCode>) {
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                database.regionQueries.deleteIn(codes.map { it.value.uppercase() })
            }
        }
    }

    override suspend fun clear() {
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                database.regionQueries.deleteAll()
            }
        }
    }

    override suspend fun count(code: CountryCode?): Int =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext if (code != null) {
                database.regionQueries.countAllInCountry(code.value.uppercase())
                    .executeAsOne()
                    .toInt()
            } else {
                database.regionQueries.countAll()
                    .executeAsOne()
                    .toInt()
            }
        }

    override suspend fun get(code: RegionCode): Region =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext database.regionQueries.selectByCode(code.value.uppercase())
                .executeAsOneOrNull()
                ?.toRegion()
                ?: throw NoSuchElementException("No '${Region::class.simpleName}' found with code '${code.value}'.")
        }

    override suspend fun get(code: CountryCode?, count: Int, offset: Int): List<Region> =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext if (code != null) {
                database.regionQueries.selectPageInCountry(
                    countryCode = code.value.uppercase(),
                    limit = count.toLong(),
                    offset = offset.toLong()
                ).executeAsList()
                    .map { it.toRegion() }
            } else {
                database.regionQueries.selectPage(limit = count.toLong(), offset = offset.toLong())
                    .executeAsList()
                    .map { it.toRegion() }
            }
        }

    override suspend fun getAll(code: CountryCode?): List<Region> =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext if (code != null) {
                database.regionQueries.selectAllForCountry(code.value.uppercase())
                    .executeAsList()
                    .map { it.toRegion() }
            } else {
                database.regionQueries.selectAll()
                    .executeAsList()
                    .map { it.toRegion() }
            }
        }

    override suspend fun getIn(codes: List<RegionCode>): List<Region> =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext database.regionQueries.selectIn(codes.map { it.value.uppercase() })
                .executeAsList()
                .map { it.toRegion() }
        }

    private fun com.mooncloak.kodetools.locale.storage.sqlite.Region.toRegion(): Region = Region(
        code = RegionCode(value = this.code),
        name = this.name,
        type = this.type,
        flag = this.flag,
        emojiFlag = this.emoji_flag
    )
}
