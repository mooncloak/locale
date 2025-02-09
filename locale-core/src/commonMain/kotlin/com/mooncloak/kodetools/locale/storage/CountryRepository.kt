package com.mooncloak.kodetools.locale.storage

import com.mooncloak.kodetools.locale.Country
import com.mooncloak.kodetools.locale.CountryCode
import kotlin.coroutines.cancellation.CancellationException

public interface CountryRepository {

    public suspend fun count(): Int

    @Throws(NoSuchElementException::class, CancellationException::class)
    public suspend fun get(code: CountryCode): Country

    public suspend fun getAll(): List<Country>

    public suspend fun getIn(codes: List<CountryCode>): List<Country> =
        getAll().filter { country -> country.code in codes }

    public suspend fun get(
        count: Int = 25,
        offset: Int = 0
    ): List<Country>

    public companion object
}

public suspend fun CountryRepository.getOrNull(code: CountryCode): Country? =
    try {
        get(code = code)
    } catch (_: NoSuchElementException) {
        null
    }
