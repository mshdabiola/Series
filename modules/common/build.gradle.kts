@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mshdabiola.common"
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:model"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
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
                implementation(libs.sqldelight.sqlite.driver)
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
