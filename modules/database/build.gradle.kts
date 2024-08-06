import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.room")
    id("com.vanniktech.maven.publish") version "0.29.0"

}
publishing {
    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/mshdabiola/series")
            credentials(PasswordCredentials::class)

        }
    }
}

mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        groupId =  libs.versions.groupId.get(),
        artifactId = "series-database",
        version = libs.versions.versionName.get()
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Series Database")
        description.set("Database for Series")
        inceptionYear.set("2024")
        url.set("https://github.com/mshdabiola/series")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        // Specify developers information
        developers {
            developer {
                id.set("mshdabiola")
                name.set("Lawal abiola")
                email.set("mshdabiola@gmail.com")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/mshdabiola/series")
        }
    }


    // Configure publishing to Maven Central
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    // Enable GPG signing for all publications
    signAllPublications()
}
android {
    namespace = "com.mshdabiola.series_database"
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