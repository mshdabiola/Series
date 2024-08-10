plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
    id("mshdabiola.android.library.publish")
}

mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        artifactId = "serieslatex",
    )
    // Configure POM metadata for the published artifact
    pom {
        name.set("Series Latex")
        description.set("Latex KMP Library")
    }
}
android {
    namespace = "com.mshdabiola.serieslatex"

}

kotlin {
//    androidTarget()
//    jvm("desktop")
    sourceSets {
        androidMain.dependencies {
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
//                implementation("org.scilab.forge:jlatexmath:1.0.7")
            }
        }
        jvmMain.dependencies {
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

