package com.mooncloak.kodetools.locale

import platform.Foundation.NSLocale
import platform.Foundation.localeWithLocaleIdentifier

@ExperimentalLocaleApi
internal actual object PlatformDefaultLocaleParser : Locale.Parser {

    actual override fun parse(languageTag: String): Locale {
        val nsLocale = NSLocale.localeWithLocaleIdentifier(languageTag)

        return IOSLocale(nsLocale)
    }

    actual override fun fromParts(
        language: String?,
        region: String?,
        script: String?,
        variants: List<String>
    ): Locale {
        val tagParts = mutableListOf<String>()

        if (!language.isNullOrBlank()) tagParts.add(language.lowercase())
        if (!script.isNullOrBlank()) tagParts.add(script.lowercase().replaceFirstChar { it.uppercase() })
        if (!region.isNullOrBlank()) tagParts.add(region.uppercase())
        if (variants.isNotEmpty()) tagParts.add(variants.joinToString(Locale.VARIANT_SEPARATOR))

        val languageTag = tagParts.joinToString("-")

        return parse(languageTag)
    }
}
