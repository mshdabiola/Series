import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.publish")
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.baselineprofile)

}


mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        artifactId = "seriesdatabase",
    )
    // Configure POM metadata for the published artifact
    pom {
        name.set("Series Database")
        description.set("Database for Series")
        inceptionYear.set("2024")
    }
}

android {
    namespace = "com.mshdabiola.seriesdatabase"
}
room {
    schemaDirectory("$projectDir/schemas")
}

kotlin {
    applyDefaultHierarchyTemplate {
        common {
            group("nonJs") {
                withAndroidTarget()
                // withIos()
                withJvm()
            }
        }
    }
    sourceSets {
        all {
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }

        wasmJsMain.dependencies{
            implementation(libs.kstore.storage)
            implementation(libs.kstore)
//            implementation(libs.kotlinx.browser)
            implementation(libs.kotlinx.serialization.json)


        }
        val nonJsMain by getting {
            dependencies {
                implementation(libs.room.runtime)
            }
        }
        jvmMain.dependencies {
            implementation(libs.sqlite.bundled)
        }
    }
}