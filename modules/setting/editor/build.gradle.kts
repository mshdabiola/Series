@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mshdabiola.setting.editor"
    //proguard here
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:model"))


                //   implementation(libs.kermit.log)

                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.androidx.dataStore.core)
            }
        }
//
//        val commonTest by getting {
//            dependencies {
//                implementation(libs.multiplatform.settings.test)
//            }
//        }
//
//        val androidMain by getting {
//            dependencies {
//                implementation(libs.multiplatform.settings.datastore)
//
//                implementation(libs.androidx.dataStore.preferences)
//            }
//        }
//
//
//        val desktopMain by getting {
//            dependencies {
//
//            }
//        }

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
