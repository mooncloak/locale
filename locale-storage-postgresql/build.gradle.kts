plugins {
    kotlin("jvm")
    kotlin("plugin.serialization")
    id("app.cash.sqldelight")
    id("locale.publish")
}

kotlin {
    explicitApi()

    sourceSets {
        all {
            // Disable warnings and errors related to these expected @OptIn annotations.
            // See: https://kotlinlang.org/docs/opt-in-requirements.html#module-wide-opt-in
            languageSettings.optIn("kotlin.RequiresOptIn")
            languageSettings.optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
            languageSettings.optIn("kotlinx.coroutines.FlowPreview")
        }
    }
}

dependencies {
    api(project(":locale-core"))

    // Coroutines
    // https://github.com/Kotlin/kotlinx.coroutines
    implementation(KotlinX.coroutines.core)

    // Time
    // https://github.com/Kotlin/kotlinx-datetime
    implementation(KotlinX.datetime)

    // Serialization
    // https://github.com/Kotlin/kotlinx.serialization
    implementation(KotlinX.serialization.json)

    // Database Adapters
    implementation("app.cash.sqldelight:primitive-adapters:_")

    // Database - Postgresql
    api("org.postgresql:postgresql:_")

    // Database Driver
    api("app.cash.sqldelight:jdbc-driver:_")
    api("app.cash.sqldelight:r2dbc-driver:_")

    // Database Pool and Cache
    implementation("com.zaxxer:HikariCP:_")
    implementation("org.ehcache:ehcache:_")

    testImplementation(Kotlin.test)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

/**
 * Creates and defines the locale database. This uses the SqlDelight library and Postgresql.
 *
 * @see [SqlDelight Documentation](https://sqldelight.github.io/sqldelight)
 */
sqldelight {
    databases {
        create("LocaleDatabase") {
            packageName.set("com.mooncloak.kodetools.locale.storage.postgresql")
            srcDirs("src/main/sqldelight")
            schemaOutputDirectory.set(file("src/main/sqldelight/databases"))
            dialect("app.cash.sqldelight:postgresql-dialect:2.0.2")
            verifyMigrations.set(false)
        }
    }
}
