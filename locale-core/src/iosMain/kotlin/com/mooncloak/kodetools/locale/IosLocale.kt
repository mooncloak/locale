package com.mooncloak.kodetools.locale

import platform.Foundation.*

@ExperimentalLocaleApi
internal class IOSLocale internal constructor(
    private val nsLocale: NSLocale
) : Locale {

    override val language: String
        get() = nsLocale.languageCode.lowercase()

    override val region: String?
        get() = nsLocale.countryCode?.uppercase()

    override val script: String?
        get() = nsLocale.scriptCode?.let { it.lowercase().replaceFirstChar { c -> c.uppercase() } }

    override val variants: List<String>
        get() = nsLocale.variantCode?.split(Locale.VARIANT_SEPARATOR)?.filter { it.isNotBlank() } ?: emptyList()

    override val languageTag: LanguageCode
        get() = LanguageCode(nsLocale.localeIdentifier)

    override fun getDisplayLanguage(inLocale: Locale): String? {
        val displayLocale = (inLocale as? IOSLocale)?.nsLocale ?: NSLocale.currentLocale()

        return displayLocale.displayNameForKey("language", value = language)
    }

    override fun getDisplayRegion(inLocale: Locale): String? {
        val displayLocale = (inLocale as? IOSLocale)?.nsLocale ?: NSLocale.currentLocale()

        return region?.let { displayLocale.displayNameForKey("country", value = it) }
    }

    override fun getDisplayScript(inLocale: Locale): String? {
        val displayLocale = (inLocale as? IOSLocale)?.nsLocale ?: NSLocale.currentLocale()

        return nsLocale.scriptCode?.let { displayLocale.displayNameForKey("script", value = it) }
    }

    override fun getDisplayVariant(inLocale: Locale): String? {
        val displayLocale = (inLocale as? IOSLocale)?.nsLocale ?: NSLocale.currentLocale()

        return variants.joinToString(", ") { variant ->
            displayLocale.displayNameForKey("variant", value = variant) ?: variant
        }.takeIf { it.isNotEmpty() }
    }

    override fun getDisplayName(inLocale: Locale): String {
        val displayLocale = (inLocale as? IOSLocale)?.nsLocale ?: NSLocale.currentLocale()

        return displayLocale.localizedStringForLocaleIdentifier(nsLocale.localeIdentifier)
    }
}
