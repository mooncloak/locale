package com.mooncloak.kodetools.locale.storage

import com.mooncloak.kodetools.locale.CountryCode
import com.mooncloak.kodetools.locale.Region
import com.mooncloak.kodetools.locale.RegionCode
import kotlin.coroutines.cancellation.CancellationException

public interface RegionRepository : LocationRepository {

    public suspend fun count(code: CountryCode? = null): Int

    @Throws(NoSuchElementException::class, CancellationException::class)
    public suspend fun get(code: RegionCode): Region

    public suspend fun getAll(code: CountryCode? = null): List<Region>

    public suspend fun getIn(codes: List<RegionCode>): List<Region> =
        getAll().filter { region -> region.code in codes }

    public suspend fun get(
        code: CountryCode? = null,
        count: Int = 25,
        offset: Int = 0
    ): List<Region>

    public companion object
}

public suspend fun RegionRepository.getOrNull(code: RegionCode): Region? =
    try {
        get(code = code)
    } catch (_: NoSuchElementException) {
        null
    }
