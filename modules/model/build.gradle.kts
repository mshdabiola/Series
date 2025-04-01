import com.vanniktech.maven.publish.SonatypeHost

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.publish")
    alias(libs.plugins.kotlin.serialization)
}


mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        artifactId = "seriesmodel",
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Series Model")
        description.set("Model for Series")
    }
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(libs.kotlinx.serialization.json)

                api(libs.kotlinx.datetime)

            }
        }
    }
}

android {
    namespace = "com.mshdabiola.seriesmodel"
}
