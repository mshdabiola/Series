/*
 * Copyright 2022 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

import com.android.build.gradle.LibraryExtension
import com.mshdabiola.app.configureGradleManagedDevices
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MppFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                pluginManager.apply("mshdabiola.mpp.library")
                pluginManager.apply("mshdabiola.mpp.library.compose")
            }
            extensions.configure<LibraryExtension> {

                defaultConfig {
//                    testInstrumentationRunner =
//                        "com.google.samples.apps.nowinandroid.core.testing.NiaTestRunner"
                }
                configureGradleManagedDevices(this)
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
                jvm("desktop")
                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                with(sourceSets) {

                    getByName("commonMain") {
                        this.dependencies {
                            implementation(libs.findLibrary("kotlinx.collection.immutable").get())
                            implementation(libs.findLibrary("kermit.log").get())
                            implementation(project(":modules:model"))
                        }

                    }
                    getByName("commonTest") {
                        this.dependencies {

                        }

                    }
                    getByName("androidMain") {
                        this.dependencies {
                            implementation(libs.findLibrary("timber").get())

                        }

                    }
                    getByName("androidInstrumentedTest") {
                        this.dependencies {
//                            implementation(kotlin("test"))
                            //  implementation(project(":core:testing"))
                        }

                    }
                    getByName("desktopMain") {
                        this.dependencies {
                            // implementation(libs.findLibrary("koin.core").get())

                        }

                    }
                    getByName("desktopTest") {
                        this.dependencies {
                            // implementation(libs.findLibrary("koin.core").get())

                        }

                    }
                }

            }
//            dependencies {
//
////                add("implementation", project(":modules:model"))
////                add("implementation", project(":modules:ui"))
////                add("implementation", project(":modules:data"))
////
////                add("implementation", libs.findLibrary("koin.core").get())
////                add("implementation", libs.findLibrary("koin.android").get())
////                add("implementation", libs.findLibrary("koin.android.compose").get())
////                add("implementation", libs.findLibrary("kotlinx-collection-immutable").get())
////
////                add("testImplementation", kotlin("test"))
////                add("testImplementation", project(":modules:testing"))
////
////                add("androidTestImplementation", kotlin("test"))
////                add("androidTestImplementation", project(":modules:testing"))
////                add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test").get())
////                add("androidTestImplementation",libs.findLibrary("androidx-test-espresso-core").get())
////                add("androidTestImplementation", libs.findLibrary("androidx-test-ext").get())
//
//            }
        }
    }
}
