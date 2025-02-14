package com.mooncloak.kodetools.locale.storage.postgresql.source

import com.mooncloak.kodetools.locale.Country
import com.mooncloak.kodetools.locale.CountryCode
import com.mooncloak.kodetools.locale.storage.MutableCountryRepository
import com.mooncloak.kodetools.locale.storage.postgresql.DatabaseProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

internal class CountrySqliteSource internal constructor(
    private val databaseProvider: DatabaseProvider,
    private val clock: Clock,
    private val dispatcher: CoroutineDispatcher
) : MutableCountryRepository {

    private val mutex = Mutex(locked = false)

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun add(country: Country): Country =
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                val now = clock.now()

                database.countryQueries.insert(
                    id = Uuid.random().toHexString(),
                    created = now,
                    updated = now,
                    code = country.code.value.uppercase(),
                    name = country.name,
                    region_type = country.regionType,
                    flag = country.flag,
                    emoji_flag = country.flag
                )

                return@withContext country
            }
        }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun addAll(countries: List<Country>) {
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                val now = clock.now()

                database.transaction {
                    countries.forEach { country ->
                        database.countryQueries.insert(
                            id = Uuid.random().toHexString(),
                            created = now,
                            updated = now,
                            code = country.code.value.uppercase(),
                            name = country.name,
                            region_type = country.regionType,
                            flag = country.flag,
                            emoji_flag = country.flag
                        )
                    }
                }
            }
        }
    }

    override suspend fun update(country: Country): Country =
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                database.countryQueries.updateAllByCode(
                    code = country.code.value.uppercase(),
                    name = country.name,
                    regionType = country.regionType,
                    flag = country.flag,
                    emojiFlag = country.emojiFlag
                )

                return@withContext country
            }
        }

    override suspend fun remove(code: CountryCode) {
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                database.countryQueries.deleteByCode(code.value.uppercase())
            }
        }
    }

    override suspend fun removeIn(codes: List<CountryCode>) {
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                database.countryQueries.deleteIn(codes.map { it.value.uppercase() })
            }
        }
    }

    override suspend fun clear() {
        withContext(dispatcher) {
            mutex.withLock {
                val database = databaseProvider.get()

                database.countryQueries.deleteAll()
            }
        }
    }

    override suspend fun count(): Int =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext database.countryQueries.countAll()
                .executeAsOne()
                .toInt()
        }

    override suspend fun get(code: CountryCode): Country =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext database.countryQueries.selectByCode(code.value.uppercase())
                .executeAsOneOrNull()
                ?.toCountry()
                ?: throw NoSuchElementException("No '${Country::class.simpleName}' found with code '${code.value}'.")
        }

    override suspend fun get(count: Int, offset: Int): List<Country> =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext database.countryQueries.selectPage(limit = count.toLong(), offset = offset.toLong())
                .executeAsList()
                .map { it.toCountry() }
        }

    override suspend fun getAll(): List<Country> =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext database.countryQueries.selectAll()
                .executeAsList()
                .map { it.toCountry() }
        }

    override suspend fun getIn(codes: List<CountryCode>): List<Country> =
        withContext(dispatcher) {
            val database = databaseProvider.get()

            return@withContext database.countryQueries.selectIn(codes = codes.map { it.value.uppercase() })
                .executeAsList()
                .map { it.toCountry() }
        }

    private fun com.mooncloak.kodetools.locale.storage.postgresql.Country.toCountry(): Country =
        Country(
            code = CountryCode(value = this.code),
            name = this.name,
            regionType = this.region_type,
            flag = this.flag,
            emojiFlag = this.emoji_flag
        )
}
