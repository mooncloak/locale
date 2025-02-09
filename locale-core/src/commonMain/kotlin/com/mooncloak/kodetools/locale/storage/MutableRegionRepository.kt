package com.mooncloak.kodetools.locale.storage

import com.mooncloak.kodetools.locale.Region
import com.mooncloak.kodetools.locale.RegionCode

public interface MutableRegionRepository : RegionRepository {

    public suspend fun add(region: Region): Region

    public suspend fun addAll(regions: List<Region>)

    public suspend fun update(region: Region): Region

    public suspend fun upsert(region: Region): Region {
        val current = this.getOrNull(code = region.code)

        return if (current == null) {
            this.add(region = region)
        } else {
            this.update(region = region)
        }
    }

    public suspend fun remove(code: RegionCode)

    public suspend fun removeIn(codes: List<RegionCode>)

    public suspend fun clear()

    public companion object
}
