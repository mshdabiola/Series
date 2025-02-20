import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.room")
    id("mshdabiola.android.library.publish")

}
mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        artifactId = "seriesdatabase",
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Series Database")
        description.set("Database for Series")
    }
}
android {
    namespace = "com.mshdabiola.seriesdatabase"
}
room {
    schemaDirectory("$projectDir/schemas")
}

configurations.commonMainApi {
            exclude(group = "org.jetbrains.kotlinx", module = "kotlinx-coroutines-android")
        }

kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":modules:model"))

            }
        }

        val jvmTest by getting {
            dependencies {
                kotlin("test")
                //    implementation(project(":core:common"))
//                implementation(project(":modules:data"))
                // api(libs.junit)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.turbine)
                implementation(libs.koin.test)
                implementation(libs.koin.test.junit)
            }
        }

        val androidUnitTest by getting{
            dependencies {
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.turbine)
                implementation(libs.koin.test)
                implementation(libs.koin.test.junit)
            }
        }

    }
}