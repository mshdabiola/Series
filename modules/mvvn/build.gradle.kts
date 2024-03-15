import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")

}

android {
    namespace = "com.mshdabiola.mvvn"
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(compose.runtime)
                implementation(compose.ui)

            }
        }

        val commonTest by getting {
            dependencies {

            }
        }

        val androidMain by getting {
            dependencies {


                api(libs.androidx.core.ktx)
                api(libs.koin.android.compose)



                api(libs.androidx.lifecycle.runtimeCompose)
                api(libs.androidx.lifecycle.viewModelCompose)
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