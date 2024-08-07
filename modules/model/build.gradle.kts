import com.vanniktech.maven.publish.SonatypeHost

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    alias(libs.plugins.kotlin.serialization)
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
        groupId = libs.versions.groupId.get(),
        artifactId = "seriesmodel",
        version = libs.versions.versionName.get()
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Series Model")
        description.set("Model for Series")
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
    namespace = "com.mshdabiola.seriesmodel"
}
