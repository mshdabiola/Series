@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

android {
    compileSdk = 34
//    buildFeatures {
//        compose = true
//    }
    kotlin {
        jvmToolchain(17)
    }
    namespace = "com.mshdabiola.testing"
}

kotlin {
    androidTarget()
    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {
                kotlin("test")
                //    implementation(project(":core:common"))
//                implementation(project(":modules:data"))
//                implementation(project(":modules:model"))
                api(libs.junit4)
                api(libs.kotlinx.coroutines.test)
                api(libs.turbine)
                api(libs.koin.test)
                api(libs.koin.test.junit)
            }
        }

        val commonTest by getting {
            dependencies {

            }
        }

        val androidMain by getting {
            dependencies {
                //  debugApi(libs.androidx.compose.ui.testManifest)
                api(libs.androidx.test.core)
                api(libs.androidx.test.espresso.core)
                api(libs.androidx.test.runner)
                api(libs.androidx.test.rules)
                api(libs.androidx.compose.ui.test)
                api(libs.koin.android.test)
            }
        }
        val androidInstrumentedTest by getting {


        }


        val desktopMain by getting {
            dependencies {

            }
        }

        val desktopTest by getting

//        val jsMain by getting {
//            dependencies {
//
//            }
//        }
    }
}
