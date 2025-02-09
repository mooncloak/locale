package com.mooncloak.kodetools.locale

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A wrapper class around a single [Country] and optional [Region].
 *
 * @property [country] The [Country].
 *
 * @property [region] The optional [Region].
 */
@Serializable
public data class CountryWithRegion public constructor(
    @SerialName(value = "country") public val country: Country,
    @SerialName(value = "region") public val region: Region? = null
)
