
import com.mshdabiola.app.libs
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.credentials.PasswordCredentials
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.credentials

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
//                signAllPublications()
            }



        }

    }
}