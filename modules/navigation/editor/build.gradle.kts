@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("kotlinx-serialization")
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