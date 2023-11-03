@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.feature")
}

android {
    namespace = "com.mshdabiola.mvvn"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }

        val commonTest by getting {
            dependencies {

            }
        }

        val androidMain by getting {
            dependencies {

                implementation(libs.koin.core)
                implementation(libs.koin.android)
                implementation(libs.koin.android.compose)

                implementation(libs.androidx.core.ktx)



                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
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