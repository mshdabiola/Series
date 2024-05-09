/*
 *abiola 2024
 */
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
//    id("mshdabiola.android.library.jacoco")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    namespace = "com.mshdabiola.ui"
}

dependencies {
    api(libs.androidx.metrics)




    androidTestImplementation(project(":modules:testing"))
}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }
    sourceSets {
        val desktopMain by getting {
            dependencies {

            }
        }

        val commonMain by getting {
            dependencies {
                api(project(":modules:analytics"))
                api(project(":modules:designsystem"))
                api(project(":modules:model"))
                api(libs.coil.kt)
                api(libs.coil.kt.compose)
                api(libs.coil.kt.svg)
                api(libs.coil.kt.network)

                api(compose.components.resources)
                implementation(project(":modules:retex"))


            }
        }
    }
}
