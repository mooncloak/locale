package com.mooncloak.kodetools.locale.storage

public interface StorageProvider : AutoCloseable {

    public override fun close()

    public val countryRepository: CountryRepository
        get() = mutableCountryRepository

    public val mutableCountryRepository: MutableCountryRepository

    public val regionRepository: RegionRepository
        get() = mutableRegionRepository

    public val mutableRegionRepository: MutableRegionRepository

    public companion object
}
