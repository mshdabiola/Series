@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
    alias(libs.plugins.secrets)
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
                implementation(project(":modules:database"))
                implementation(project(":modules:setting:editor"))
                implementation(libs.kotlinx.coroutines.core)
            }
        }
        val desktopMain by getting{
            dependencies {
                implementation(project(":modules:svgtovector"))

            }
        }


    }
}