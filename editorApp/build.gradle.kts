import com.mshdabiola.app.BuildType
import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)

    id("mshdabiola.android.application")
    id("mshdabiola.android.application.compose")
    id("mshdabiola.android.application.jacoco")
    id("jacoco")
    id("mshdabiola.android.application.firebase")
    alias(libs.plugins.androidx.baselineprofile)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.roborazzi)
}
dependencies {

    implementation(project(":modules:app:editor"))
    implementation(project(":modules:model"))
    implementation(project(":modules:data:editor"))
    implementation(project(":modules:navigation:editor"))
    implementation(project(":modules:analytics"))
    implementation(project(":modules:mvvn"))
    implementation(project(":modules:designsystem"))



    implementation(libs.koin.android.compose)
    implementation(libs.koin.android)

    implementation(libs.androidx.metrics)

    implementation(libs.timber)
    debugImplementation(libs.leakcanary.android)

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.tracing.ktx)
    implementation(libs.androidx.profileinstaller)

    debugImplementation(libs.androidx.compose.ui.testManifest)


    testImplementation(project(":modules:testing"))
    testImplementation(libs.accompanist.testharness)

    testImplementation(libs.robolectric)
    testImplementation(libs.roborazzi)

    androidTestImplementation(project(":modules:testing"))
    androidTestImplementation(libs.accompanist.testharness)
    debugImplementation(libs.androidx.monitor)
//    baselineProfile(project(":benchmarks"))
}

kotlin {
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//            }
//        }
//        binaries.executable()
//    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
//            implementation(project(":shared"))

            implementation(libs.androidx.compose.material3.windowSizeClass)

            implementation(libs.decompose.core)
            implementation(libs.decompose.compose.jetbrains)

            implementation(libs.koin.core)

            implementation(project(":modules:designsystem"))
            implementation(project(":modules:analytics"))
            implementation(project(":modules:mvvn"))
            implementation(project(":modules:navigation:editor"))
        }

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(project(":modules:app:editor"))

            implementation(libs.kotlinx.coroutines.swing)

        }
    }
}

android {

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/composeResources")

    namespace = "com.mshdabiola.series.editor"

    defaultConfig {
        applicationId = "com.mshdabiola.series.editor"
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = minor, Z = Patch level

        // Custom test runner to set up Hilt dependency graph
        testInstrumentationRunner = "com.mshdabiola.testing.TestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix
        }
        val release = getByName("release") {
            isMinifyEnabled = true
            applicationIdSuffix = BuildType.RELEASE.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            // signingConfig = signingConfigs.getByName("debug")
            // Ensure Baseline Profile is fresh for release builds.
            baselineProfile.automaticGenerationDuringBuild = true
        }
        create("benchmark") {
            // Enable all the optimizations from release build through initWith(release).
            initWith(release)
            matchingFallbacks.add("release")
            // Debug key signing is available to everyone.
            signingConfig = signingConfigs.getByName("debug")
            // Only use benchmark proguard rules
            proguardFiles("benchmark-rules.pro")
            isMinifyEnabled = true
            applicationIdSuffix = BuildType.BENCHMARK.applicationIdSuffix
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    dependencies {
        debugImplementation(libs.compose.ui.tooling)
    }
}

compose.desktop {
    application {
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

        val iconsRoot = project.file("src/commonMain/composeResources/drawable/launcher")
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageVersion = "1.0.2"
            packageName = "Series Editor"
            description = "For setting question"
            copyright = "© 2022 Mshdabiola. All rights reserved."
            vendor = "Mshdabiola App"
            version = "1.0.2"
            licenseFile.set(rootProject.file("LICENSE"))

            modules("java.net.http", "java.sql")

            linux {
                iconFile.set(iconsRoot.resolve("linux.png"))
                debMaintainer = "mshdabiola@gmail.com"
                menuGroup = packageName
                appCategory = "Education"
            }

            windows {
                iconFile.set(iconsRoot.resolve("windows.ico"))
                shortcut = true
                menuGroup = packageName
//                https://www.guidgen.com/   generate uuid
                //https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "c51e7958-c613-421b-a894-62a7d10a209a"
            }

            macOS {
                iconFile.set(iconsRoot.resolve("macos.icns"))
                bundleID = "com.mshdabiola.series.editor"
                appCategory = "public.app-category.productivity"
                signing {
                    sign.set(false)
                }
            }
        }
    }
}

//
//compose.experimental {
//    web.application {}
//}


baselineProfile {
    // Don't build on every iteration of a full assemble.
    // Instead enable generation directly for the release build variant.
    automaticGenerationDuringBuild = false
}

dependencyGuard {
    configuration("releaseRuntimeClasspath")
}
task("testClasses")
