plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
}

android {
    namespace = "com.mshdabiola.retex"

}

kotlin {
//    androidTarget()
//    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:jretex"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
            }
        }

        val jvmTest by getting {
            dependencies {
                kotlin("test")
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.turbine)
                implementation(libs.koin.test)
                implementation(libs.koin.test.junit)
            }
        }


    }
}

