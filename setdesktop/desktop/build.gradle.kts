import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
//    kotlin("kapt")
    id("org.jetbrains.compose") version "1.4.0"


}

dependencies {
    // Compose
    implementation(compose.desktop.currentOs)
    implementation(compose.ui)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    implementation(compose.desktop.components.splitPane)
    @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
    implementation(compose.desktop.components.animatedImage)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
    implementation(compose.animation)
    implementation(compose.animationGraphics)
    implementation(libs.kotlinx.collection.immutable)

    implementation(compose.desktop.currentOs)
    implementation(project(":modules:designsystem"))
    implementation(project(":modules:data"))
    implementation(project(":modules:model"))
    implementation(project(":modules:retex"))
    implementation(project(":modules:common"))

    implementation(project(":modules:ui"))
    implementation(libs.kotlinx.coroutines.swing)
    testImplementation(kotlin("test"))
    testImplementation(project(":modules:testing"))

    // Dagger : A fast dependency injector for Android and Java.
//    kapt("com.google.dagger:dagger-compiler:2.44.2")
//    api("com.google.dagger:dagger:2.44.2")

    // Decompose : Decompose
    val decomposeVersion = "2.0.0-alpha-02"
    implementation(libs.decompose.core)
    //implementation(libs.decompose.android)
    implementation(libs.decompose.compose.jetbrains)
    implementation(libs.koin.core)


}

compose.desktop {
    application {
        mainClass = "com.mshdabiola.series.MainAppKt"
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
            packageVersion = "1.0.0"
            packageName = "Series"
            description = "For setting question"
            copyright = "© 2022 Mshdabiola. All rights reserved."
            vendor = "Mshdabiola App"
            version = "1.0.0"
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
                //https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "791AC64E-Y9A7-4CBF-A1C4-AFE5CFFDDDFT"
            }

            macOS {
                iconFile.set(iconsRoot.resolve("macos.icns"))
                bundleID = "com.mshdabiola.series"
                appCategory = "public.app-category.productivity"
                signing {
                    sign.set(false)
                }
            }
        }
    }
}
//./gradlew packageUberJarForCurrentOS package
//./gradlew packageUberJarForCurrentOS
//    ./gradlew packageDmg -Pcompose.desktop.mac.sign=true
