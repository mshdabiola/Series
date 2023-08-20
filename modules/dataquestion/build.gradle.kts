@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.mshdabiola.dataquestion"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(libs.koin.core)
                implementation(project(":modules:model"))
                implementation(project(":modules:database"))
                implementation(project(":modules:settingquestion"))
                implementation(libs.kotlinx.coroutines.core)
            }
        }



    }
}
