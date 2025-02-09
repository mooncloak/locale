package com.mooncloak.kodetools.locale.storage

import com.mooncloak.kodetools.locale.Country
import com.mooncloak.kodetools.locale.CountryCode

public interface MutableCountryRepository : CountryRepository {

    public suspend fun add(country: Country): Country

    public suspend fun addAll(countries: List<Country>)

    public suspend fun update(country: Country): Country

    public suspend fun upsert(country: Country): Country {
        val current = this.getOrNull(code = country.code)

        return if (current == null) {
            this.add(country = country)
        } else {
            this.update(country = country)
        }
    }

    public suspend fun remove(code: CountryCode)

    public suspend fun removeIn(codes: List<CountryCode>)

    public suspend fun clear()

    public companion object
}
