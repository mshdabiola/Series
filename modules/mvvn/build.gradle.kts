@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.feature")
}

android {
    namespace = "com.mshdabiola.mvvn"
}

kotlin {
    sourceSets {

        val androidMain by getting {
            dependencies {

//                implementation(libs.koin.core)
                implementation(libs.koin.android)
                implementation(libs.koin.android.compose)

                // implementation(libs.androidx.core.ktx)


                implementation(libs.androidx.lifecycle.runtimeCompose)
//                implementation(libs.viewmodel.core)
//                implementation(libs.viewmodel.compose)
            }
        }
    }
}