import org.jetbrains.compose.ExperimentalComposeLibrary

/*
 *abiola 2024
 */
plugins {
    id("mshdabiola.android.library")
}

android {
    namespace = "com.mshdabiola.testing"
}
dependencies {
//
//
//    debugApi(libs.androidx.compose.ui.testManifest)
//
}
kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test"))
                implementation(projects.modules.database)
                implementation(projects.modules.model)
                api(libs.kotlinx.coroutines.test)
                api(libs.turbine)
                api(libs.koin.test)
                api(libs.koin.test.junit)
            }
        }




        val androidMain by getting {
            dependencies {
            }
        }
    }
}