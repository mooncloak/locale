package com.mooncloak.kodetools.locale

@ExperimentalLocaleApi
internal class JvmLocale internal constructor(
    private val jvmLocale: java.util.Locale
) : Locale {

    override val language: String?
        get() = jvmLocale.language.takeIf { it.isNotBlank() }

    override val region: String?
        get() = jvmLocale.country.takeIf { it.isNotBlank() }

    override val script: String?
        get() = jvmLocale.script.takeIf { it.isNotBlank() }

    override val variants: List<String>
        get() = jvmLocale.variant.takeIf { it.isNotBlank() }?.split(Locale.VARIANT_SEPARATOR) ?: emptyList()

    override val languageTag: LanguageCode
        get() = LanguageCode(value = jvmLocale.toLanguageTag())

    override fun getDisplayLanguage(inLocale: Locale): String? =
        jvmLocale.getDisplayLanguage(inLocale.toJvmLocale().jvmLocale).takeIf { it.isNotBlank() }

    override fun getDisplayRegion(inLocale: Locale): String? =
        jvmLocale.getDisplayCountry(inLocale.toJvmLocale().jvmLocale).takeIf { it.isNotBlank() }

    override fun getDisplayScript(inLocale: Locale): String? =
        jvmLocale.getDisplayScript(inLocale.toJvmLocale().jvmLocale).takeIf { it.isNotBlank() }

    override fun getDisplayVariant(inLocale: Locale): String? =
        jvmLocale.getDisplayVariant(inLocale.toJvmLocale().jvmLocale).takeIf { it.isNotBlank() }

    override fun getDisplayName(inLocale: Locale): String? =
        jvmLocale.getDisplayName(inLocale.toJvmLocale().jvmLocale).takeIf { it.isNotBlank() }

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Locale) return false

        return languageTag == other.languageTag &&
                region == other.region &&
                script == other.script &&
                variants == other.variants
    }

    override fun hashCode(): Int {
        var result = language?.hashCode() ?: 0

        result = 31 * result + (region?.hashCode() ?: 0)
        result = 31 * result + (script?.hashCode() ?: 0)
        result = 31 * result + variants.hashCode()

        return result
    }

    override fun toString(): String =
        "Locale(language=$language,region=$region,script=$script,variants=$variants)"
}

@ExperimentalLocaleApi
private fun Locale.toJvmLocale(): JvmLocale =
    (this as? JvmLocale) ?: JvmLocale(java.util.Locale.forLanguageTag(languageTag.value))
