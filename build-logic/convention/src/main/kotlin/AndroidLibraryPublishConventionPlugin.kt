
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import com.mshdabiola.app.configureFlavors
import com.mshdabiola.app.configureGradleManagedDevices
import com.mshdabiola.app.configureKotlinAndroid
import com.mshdabiola.app.configurePrintApksTask
import com.mshdabiola.app.disableUnnecessaryAndroidTests
import com.mshdabiola.app.libs
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.credentials
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.powerassert.gradle.PowerAssertGradleExtension
import java.io.File
import java.util.Properties

class AndroidLibraryPublishConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {

                apply("com.vanniktech.maven.publish")
            }
            extensions.configure<PublishingExtension> {
                repositories {
                    maven {
                        name = "githubPackages"
                        url = uri("https://maven.pkg.github.com/mshdabiola/series")
                        credentials(PasswordCredentials::class)

                    }
                }
            }
            extensions.configure<MavenPublishBaseExtension> {
                // Define coordinates for the published artifact
                coordinates(
                    groupId = libs.findVersion("groupId").get().toString(),
                    version = libs.findVersion("versionName").get().toString(),
                )

                // Configure POM metadata for the published artifact
                pom {

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

                // Configure publishing to Maven Central
                publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

                // Enable GPG signing for all publications
                signAllPublications()
            }



        }

    }
}