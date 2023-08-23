@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.mshdabiola.model"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val desktopMain by getting {
            dependencies {

            }
        }


    }
}
//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
//    kotlinOptions {
//        jvmTarget = "17"
//    }
//}