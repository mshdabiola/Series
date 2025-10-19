/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    alias(libs.plugins.kotlin.serialization)
    id("mshdabiola.android.library.publish")
    alias(libs.plugins.baselineprofile)
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

dependencies {
    baselineProfile(projects.benchmarks)

}

baselineProfile {
    baselineProfileOutputDir = "../../src/androidMain"
    filter {
        include("com.mshdabiola.seriesmodel.**")
    }
}
kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation::class)
    abiValidation {
        // Use the set() function to ensure compatibility with older Gradle versions
        enabled.set(true)
    }
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
