package com.mooncloak.kodetools.locale

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A wrapper class around a single [Country] and zero or more [Region]s.
 *
 * @property [country] The [Country].
 *
 * @property [regions] The [Region]s.
 */
@Serializable
public data class CountryAndRegions public constructor(
    @SerialName(value = "country") public val country: Country,
    @SerialName(value = "regions") public val regions: List<Region> = emptyList()
)
