package com.mooncloak.kodetools.locale

/**
 * Represents a [Country] or [Region] location.
 *
 * @property [code] The [LocationCode] of this location.
 *
 * @property [name] The name of this location.
 */
public sealed interface Location {

    public val code: LocationCode

    public val name: String?

    public companion object
}
