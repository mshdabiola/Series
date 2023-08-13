@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
}

android {
    namespace = "com.mshdabiola.data"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:model"))

                implementation(libs.koin.core)
                implementation(project(":modules:model"))
                implementation(project(":modules:database"))
                implementation(project(":modules:setting"))
                implementation(libs.kotlinx.coroutines.core)
            }
        }


    }
}