@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    alias(libs.plugins.kotlin.serialization)

//    id("maven-publish")
}
//group = "com.mshdabiola.model"
//version = "1.0.0"

//publishing {
//    repositories {
//        maven {
//            //...
//        }
//    }
//}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(libs.kotlinx.serialization.json)

            }
        }
    }
}

android {
    namespace = "com.mshdabiola.generalmodel"
}
