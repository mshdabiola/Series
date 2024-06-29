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

import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.mshdabiola.app.configureFlavors
import com.mshdabiola.app.configureGradleManagedDevices
import com.mshdabiola.app.configureKotlinAndroid
import com.mshdabiola.app.configurePrintApksTask
import com.mshdabiola.app.disableUnnecessaryAndroidTests
import com.mshdabiola.app.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension
import java.io.File
import java.util.Properties

class AndroidLibraryConventionPlugin : Plugin<Project> {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-multiplatform")
                apply("com.android.library")
                apply("mshdabiola.android.lint")
                apply( "org.jetbrains.kotlin.plugin.power-assert")
                apply("maven-publish")
            }

            group = "com.mshdabiola.series"
            version = libs.findVersion("versionName").get().toString()

            extensions.configure<PowerAssertGradleExtension> {
                functions.set(
                    listOf(
                        "kotlin.assert",
                        "kotlin.test.assertTrue",
                        "kotlin.test.assertEquals",
                        "kotlin.test.assertNull"
                    )
                )
            }

            var project : Properties?=null
            try {
                project=  File(rootDir, "local.properties").inputStream().use {
                    Properties().apply { load(it) }

                }
                // println("user ${ project?.getProperty("gpr.password")}")


            } catch (e: Exception) {

                //e.printStackTrace()
            }

            extensions.configure<PublishingExtension> {
                repositories {
                    maven {
                        name = "library"
                        url = uri("https://maven.pkg.github.com/mshdabiola/series")
                        credentials {
                            username = project?.getProperty("gpr.userid")  ?: System.getenv("USERID")
                            password = project?.getProperty("gpr.password") ?: System.getenv("PASSWORD")
                        }
                    }
                }
            }


            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 34
                //configureFlavors(this)
                configureGradleManagedDevices(this)
                // The resource prefix is derived from the module name,
                // so resources inside ":core:module1" must be prefixed with "core_module1_"
                resourcePrefix =
                    path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_")
                        .lowercase() + "_"
            }
            extensions.configure<LibraryAndroidComponentsExtension> {
                configurePrintApksTask(this)
                disableUnnecessaryAndroidTests(target)
            }
            dependencies {
                add("testImplementation", kotlin("test"))
                add("implementation", libs.findLibrary("androidx.tracing.ktx").get())
            }
            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget{
                    publishLibraryVariants("release", "debug")

                }
                // jvm("desktop")
                jvm()
                jvmToolchain(17)

                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                with(sourceSets) {

                    getByName("commonMain") {
                        this.dependencies {
                            implementation(libs.findLibrary("koin.core").get())
                            implementation(libs.findLibrary("kermit").get())

                        }

                    }
                    getByName("commonTest") {
                        this.dependencies {
                            // implementation(libs.findLibrary("koin.core").get())
                            implementation(kotlin("test"))

                        }

                    }
                    getByName("androidMain") {
                        this.dependencies {
                            implementation(libs.findLibrary("koin.android").get())
                        }

                    }
                    getByName("androidInstrumentedTest") {
                        this.dependencies {
//                            implementation(kotlin("test"))
                            //  implementation(project(":core:testing"))
                           // implementation(project(":modules:testing"))
                        }

                    }
                    getByName("jvmMain") {
                        this.dependencies {
                            implementation(libs.findLibrary("slf4j.simple").get())


                        }

                    }
                    getByName("jvmTest") {
                        this.dependencies {
                            // implementation(libs.findLibrary("koin.core").get())
//                            implementation(project(":modules:testing"))
                        }

                    }
                }

            }
        }

    }
}