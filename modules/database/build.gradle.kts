import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.room")
//    kotlin("plugin.power-assert") version "2.0.0"



}
// build.gradle.kts


android {
    namespace = "com.mshdabiola.database"
}
room {
    schemaDirectory("$projectDir/schemas")
}

configurations.commonMainApi {
            exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
        }

kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":modules:model"))

            }
        }

        val jvmTest by getting {
            dependencies {
                kotlin("test")
                //    implementation(project(":core:common"))
//                implementation(project(":modules:data"))
                // api(libs.junit)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.turbine)
                implementation(libs.koin.test)
                implementation(libs.koin.test.junit)
            }
        }

        val androidUnitTest by getting{
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.turbine)
                implementation(libs.koin.test)
                implementation(libs.koin.test.junit)
            }
        }

    }
}