
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    alias(libs.plugins.kotlin.serialization)
    id("mshdabiola.android.library.publish")


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
        inceptionYear.set("2024")
    }
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
