@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
}

android {
    namespace = "com.mshdabiola.data.cbt"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:model"))

                implementation(libs.koin.core)
                implementation(project(":modules:model"))
                implementation(project(":modules:analytics"))

                implementation(project(":modules:database"))
                implementation(project(":modules:setting:editor"))
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.generativeai.google)
                // implementation(project(":modules:svgtovector"))
            }
        }
        val desktopMain by getting {
            dependencies {


            }
        }


    }
}