@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
    id("mshdabiola.mpp.library.compose")
    id("org.jetbrains.compose") version "1.4.0"
}

android {
    namespace = "com.mshdabiola.retex"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":common:jretex"))
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.materialIconsExtended)
                implementation(compose.material3)
                implementation(libs.kotlinx.collection.immutable)
            }
        }

        val commonTest by getting {
            dependencies {

            }
        }

        val androidMain by getting {
            dependencies {

            }
        }


        val desktopMain by getting {
            dependencies {
                implementation(compose.preview)
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