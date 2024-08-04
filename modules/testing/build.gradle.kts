import org.jetbrains.compose.ExperimentalComposeLibrary

/*
 *abiola 2024
 */
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
}

android {
    namespace = "com.mshdabiola.testing"
}
dependencies {
//
//
   // debugApi(libs.androidx.compose.ui.testManifest)
//
}
kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test"))
                implementation(compose.runtime)
                implementation(projects.modules.database)
                implementation(projects.modules.model)
                api(libs.kotlinx.coroutines.test)
                api(libs.turbine)
                api(libs.koin.test)
                api(libs.koin.test.junit)
            }
        }

        val jvmMain by getting {
            dependencies {
                api(compose.desktop.currentOs)

                api(compose.desktop.uiTestJUnit4)
            }
        }


        val androidMain by getting {
            dependencies {
                api(libs.androidx.test.core)
                // api(libs.androidx.test.espresso.core)
                //api(libs.androidx.test.runner)
                // api(libs.androidx.test.rules)


               // api(libs.androidx.compose.ui.test)
                api(libs.koin.android.test)
            }
        }
    }
}