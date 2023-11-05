import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.mshdabiola.app.configureFlavors
import com.mshdabiola.app.configureGradleManagedDevices
import com.mshdabiola.app.configureKotlinAndroid
import com.mshdabiola.app.configurePrintApksTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
//                compileSdk=33
//                defaultConfig.targetSdk = 33
//                compileSdkPreview = "UpsideDownCake"
                defaultConfig.targetSdk = 34
                defaultConfig.minSdk = 24
                defaultConfig.versionName = "05.11.2023"
                defaultConfig.versionCode = 9

                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                defaultConfig.vectorDrawables {
                    useSupportLibrary = true
                }
                // defaultConfig.resourceConfigurations+= listOf("en")
                configureFlavors(this)
                configureGradleManagedDevices(this)

            }
            extensions.configure<ApplicationAndroidComponentsExtension> {
                configurePrintApksTask(this)
            }

            tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
                kotlinOptions {
                    jvmTarget = "17"
                }
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            dependencies {
                add("implementation", project(":modules:model"))
                //    add("implementation", project(":modules:ui"))
                //add("implementation", project(":modules:util"))
                add("implementation", libs.findLibrary("koin.core").get())
                add("implementation", libs.findLibrary("koin.android").get())
                add("implementation", libs.findLibrary("koin.android.compose").get())
                add("implementation", libs.findLibrary("kotlinx-collection-immutable").get())

                add("testImplementation", kotlin("test"))
                add("testImplementation", project(":modules:testing"))
                add("androidTestImplementation", kotlin("test"))
                add("androidTestImplementation", project(":modules:testing"))
                add("androidTestImplementation", libs.findLibrary("androidx-compose-ui-test").get())
                add(
                    "androidTestImplementation",
                    libs.findLibrary("androidx-test-espresso-core").get()
                )
                add("androidTestImplementation", libs.findLibrary("androidx-test-ext").get())


            }

        }
    }

}