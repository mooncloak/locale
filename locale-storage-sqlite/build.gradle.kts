import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("com.android.library")
    id("org.jetbrains.dokka")
    id("locale.publish")
    id("app.cash.sqldelight")
}

kotlin {
    applyDefaultHierarchyTemplate()

    macosX64()
    macosArm64()

    linuxArm64()
    linuxX64()

    mingwX64()

    iosArm64()
    iosX64()
    iosSimulatorArm64()

    androidTarget {
        publishAllLibraryVariants()
    }

    jvm()

    explicitApi()

    sourceSets {
        all {
            // Disable warnings and errors related to these expected @OptIn annotations.
            // See: https://kotlinlang.org/docs/opt-in-requirements.html#module-wide-opt-in
            languageSettings.optIn("kotlin.RequiresOptIn")
            languageSettings.optIn("-Xexpect-actual-classes")
        }

        val commonMain by getting {
            dependencies {
                api(project(":locale-core"))

                // Coroutines
                // https://github.com/Kotlin/kotlinx.coroutines
                implementation(KotlinX.coroutines.core)

                // Serialization
                // https://github.com/Kotlin/kotlinx.serialization
                implementation(KotlinX.serialization.json)

                // Time
                // https://github.com/Kotlin/kotlinx-datetime
                implementation(KotlinX.datetime)

                // Database Adapters
                implementation("app.cash.sqldelight:primitive-adapters:_")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(KotlinX.coroutines.test)
            }
        }

        val jvmMain by getting {
            dependencies {
                // Database Driver - SQLite
                api("app.cash.sqldelight:sqlite-driver:_")
            }
        }

        val androidMain by getting {
            dependencies {
                // Database Driver - SQLite
                api("app.cash.sqldelight:android-driver:_")
            }
        }

        val nativeMain by getting {
            dependencies {
                // Database Driver - SQLite
                api("app.cash.sqldelight:native-driver:_")
            }
        }
    }
}

android {
    compileSdk = BuildConstants.Android.compileSdkVersion
    namespace = "com.mooncloak.kodetools.locale.storage.sqlite"

    defaultConfig {
        minSdk = BuildConstants.Android.minSdkVersion
        targetSdk = BuildConstants.Android.targetSdkVersion
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            // Opt-in to experimental compose APIs
            freeCompilerArgs = listOf(
                "-Xopt-in=kotlin.RequiresOptIn"
            )
        }
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].java.srcDirs("src/androidMain/kotlin")
    sourceSets["main"].res.srcDirs("src/androidMain/res")

    sourceSets["test"].java.srcDirs("src/androidTest/kotlin")
    sourceSets["test"].res.srcDirs("src/androidTest/res")
}

/**
 * Creates and defines the locale database. This uses the SqlDelight library and SQLite.
 *
 * @see [SqlDelight Documentation](https://sqldelight.github.io/sqldelight)
 */
sqldelight {
    databases {
        create("LocaleDatabase") {
            packageName.set("com.mooncloak.kodetools.locale.storage.sqlite")
            srcDirs("src/commonMain/sqldelight")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            verifyMigrations.set(false)
        }
    }
}
