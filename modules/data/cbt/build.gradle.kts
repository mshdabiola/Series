@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
    alias(libs.plugins.secrets)
}

android {
    namespace = "com.mshdabiola.data.editor"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(libs.koin.core)
                implementation(project(":modules:model"))
                implementation(project(":modules:database"))
                implementation(project(":modules:setting:cbt"))
                implementation(libs.kotlinx.coroutines.core)
            }
        }



    }
}
