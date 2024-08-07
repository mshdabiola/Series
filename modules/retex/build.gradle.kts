import com.vanniktech.maven.publish.SonatypeHost

plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
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
        artifactId = "serieslatex",
        version =libs.versions.versionName.get()
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Series Latex")
        description.set("Latex KMP Library")
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
    namespace = "com.mshdabiola.serieslatex"

}

kotlin {
//    androidTarget()
//    jvm("desktop")
    sourceSets {
        androidMain.dependencies{
            api(compose.preview)

        }
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:jretex"))
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                implementation(compose.components.resources)
            }
        }
        jvmMain.dependencies{
            api(compose.preview)

        }

        val jvmTest by getting {
            dependencies {
                kotlin("test")
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.turbine)
                implementation(libs.koin.test)
                implementation(libs.koin.test.junit)
            }
        }


    }
}

