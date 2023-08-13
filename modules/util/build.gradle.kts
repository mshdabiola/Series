@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
}

android {
    namespace = "com.mshdabiola.util"
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
              //  implementation("com.android.tools:sdk-common:31.1.0")

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
//
//dependencies {
//
//    testImplementation(project(":core:testing"))
//    implementation(libs.paging.runtime)
//    implementation(libs.paging.common)
//}