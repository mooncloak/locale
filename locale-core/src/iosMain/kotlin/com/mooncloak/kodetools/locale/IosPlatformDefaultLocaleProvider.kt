package com.mooncloak.kodetools.locale

import platform.Foundation.NSLocale
import platform.Foundation.availableLocaleIdentifiers
import platform.Foundation.currentLocale
import platform.Foundation.localeWithLocaleIdentifier

@ExperimentalLocaleApi
internal actual object PlatformDefaultLocaleProvider : Locale.Provider {

    actual override fun getDefault(): Locale {
        val nsLocale = NSLocale.currentLocale()

        return IOSLocale(nsLocale)
    }

    actual override fun getAvailable(): List<Locale> {
        val identifiers = NSLocale.availableLocaleIdentifiers()

        return identifiers.mapNotNull { id ->
            (id as? String)?.let { IOSLocale(NSLocale.localeWithLocaleIdentifier(it)) }
        }
    }

    actual val US: Locale
        get() = PlatformDefaultLocaleParser.parse("en-US")

    actual val ENGLISH: Locale
        get() = PlatformDefaultLocaleParser.parse("en")

    actual val EN_US: Locale
        get() = PlatformDefaultLocaleParser.parse("en-US")
}
