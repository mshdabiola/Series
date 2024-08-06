
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
                apply("org.jetbrains.kotlin.plugin.power-assert")
              //  apply("maven-publish")
            }

            //includedSourceSets = listOf("commonMain", "jvmMain", "jsMain", "nativeMain")

            extensions.configure<PowerAssertGradleExtension> {
                functions.set(
                    listOf(
                        "kotlin.assert",
                        "kotlin.test.assertTrue",
                        "kotlin.test.assertEquals",
                        "kotlin.test.assertNull",
                    ),
                )
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
                androidTarget {
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
                            implementation(project(":modules:testing"))
                        }

                    }
                }

            }
        }

    }
}