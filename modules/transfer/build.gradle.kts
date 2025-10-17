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
@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.publish")
    alias(libs.plugins.baselineprofile)

}


mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        artifactId = "ui",
    )
    // Configure POM metadata for the published artifact
    pom {
        name.set("sample transfer")
        description.set("ui")
    }
}


android {
    namespace = "com.mshdabiola.transfer"
}
dependencies {
    baselineProfile(projects.benchmarks)

}

baselineProfile {
    baselineProfileOutputDir = "../../src/androidMain"
    filter {
        include("com.mshdabiola.transfer.**")
    }
}
kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation::class)
    abiValidation {
        // Use the set() function to ensure compatibility with older Gradle versions
        enabled.set(true)
    }
    applyDefaultHierarchyTemplate {
        common {
            group("nonJs") {
                withAndroidTarget()
                // withIos()
                withJvm()
            }
        }
    }
    sourceSets {
         commonMain.dependencies {
             implementation(libs.kotlinx.serialization.json)
             implementation(projects.modules.database)
             implementation(projects.modules.model)
             implementation(libs.koin.core)
             implementation(libs.kotlinx.coroutines.core)
         }

    }
}
