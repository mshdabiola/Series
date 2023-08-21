@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
    id("kotlin-parcelize")
}

android {
    namespace = "com.mshdabiola.navigation.editor"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.decompose.core)
                //implementation(libs.decompose.android)
                implementation(libs.decompose.compose.jetbrains)
            }
        }
    }
}