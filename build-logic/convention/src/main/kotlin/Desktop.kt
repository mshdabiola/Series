import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class Desktop : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-multiplatform")
                apply("org.jetbrains.compose")
                //apply("org.jetbrains.kotlin.android")

            }
            val de = extensions.getByType<DesktopExtension>()
            val compose = extensions.getByType<ComposeExtension>()
            extensions.configure<KotlinMultiplatformExtension> {
                //android()
                jvm("desktop")
                val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
                with(sourceSets) {

                    getByName("commonMain") {
                        this.dependencies {
                            implementation(libs.findLibrary("koin.core").get())

                        }

                    }
                    getByName("commonTest") {
                        this.dependencies {
                            // implementation(libs.findLibrary("koin.core").get())

                        }

                    }
                    getByName("androidMain") {
                        this.dependencies {
                            // implementation(libs.findLibrary("koin.core").get())

                        }

                    }
                    getByName("androidInstrumentedTest") {
                        this.dependencies {
                            // implementation(libs.findLibrary("koin.core").get())

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
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", compose.dependencies.desktop.currentOs)
                add("implementation", compose.dependencies.ui)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                add("implementation", compose.dependencies.desktop.components.splitPane)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                add("implementation", compose.dependencies.desktop.components.animatedImage)
                add("implementation", compose.dependencies.material3)
                add("implementation", compose.dependencies.materialIconsExtended)
                add("implementation", compose.dependencies.animation)
                add("implementation", compose.dependencies.animationGraphics)
                add("implementation", libs.findLibrary("kotlinx.collection.immutable").get())

                add("implementation", compose.dependencies.desktop.currentOs)
                add("implementation", project(":common:designsystem"))
                add("implementation", project(":common:data"))
                add("implementation", project(":common:model"))

                add("implementation", project(":common:ui"))
                add("implementation", libs.findLibrary("kotlinx.coroutines.swing").get())

                add("implementation", libs.findLibrary("decompose.core").get())
                add("implementation", libs.findLibrary("decompose.compose.jetbrains").get())
                add("implementation", libs.findLibrary("koin.core").get())
            }
            de.application {
                mainClass = "com.mshdabiola.desktop.MainAppKt"
                /*
                * Its unreliable. Don't run release tasks for now.
                * Wait until fixed: https://github.com/JetBrains/compose-jb/issues/2393
                */

                buildTypes.release.proguard {
                    configurationFiles.from(project.file("compose-desktop.pro"))
                    obfuscate.set(true)
                    version.set("7.3.0")
                }

                val iconsRoot = project.file("src/main/resources/drawables/launcher")
                nativeDistributions {
                    targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
                    packageVersion = "1.0.1"
                    packageName = "Skeleton App"
                    description = "Template"
                    copyright = "© 2022 Mshdabiola. All rights reserved."
                    vendor = "Mshdabiola App"
                    version = "1.0.1"
                    licenseFile.set(rootProject.file("LICENSE"))

                    modules("java.net.http", "java.sql")

                    linux {
                        iconFile.set(iconsRoot.resolve("linux.png"))
                        debMaintainer = "mshdabiola@gmail.com"
                        menuGroup = packageName
                        appCategory = "Productivity"
                    }

                    windows {
                        iconFile.set(iconsRoot.resolve("windows.ico"))
                        shortcut = true
                        menuGroup = packageName
                        //https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                        upgradeUuid = "791AC64E-C9A7-4CBF-A1C4-AFE5CFFDDDFA"
                    }

                    macOS {
                        iconFile.set(iconsRoot.resolve("macos.icns"))
                        bundleID = "com.mshdabiola.skeleton"
                        appCategory = "public.app-category.productivity"
                        signing {
                            sign.set(false)
                        }
                    }
                }
            }
        }
    }

}