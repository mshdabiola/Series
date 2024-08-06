import org.jetbrains.compose.ExperimentalComposeLibrary

/*
 *abiola 2024
 */
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
        groupId =  libs.versions.groupId.get(),
        artifactId = "testing",
        version =libs.versions.versionName.get()
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("J retext KMP Library")
        description.set("Sample Kotlin MultiPlatform Library Test")
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
}
android {
    namespace = "com.mshdabiola.testing"
}
dependencies {
//
//
   // debugApi(libs.androidx.compose.ui.testManifest)
//
}
kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(kotlin("test"))
                implementation(compose.runtime)
                implementation(projects.modules.database)
                implementation(projects.modules.model)
                api(libs.kotlinx.coroutines.test)
                api(libs.turbine)
                api(libs.koin.test)
                api(libs.koin.test.junit)
            }
        }

        val jvmMain by getting {
            dependencies {
                api(compose.desktop.currentOs)

                api(compose.desktop.uiTestJUnit4)
            }
        }


        val androidMain by getting {
            dependencies {
                api(libs.androidx.test.core)
                // api(libs.androidx.test.espresso.core)
                //api(libs.androidx.test.runner)
                // api(libs.androidx.test.rules)


               // api(libs.androidx.compose.ui.test)
                api(libs.koin.android.test)
            }
        }
    }
}