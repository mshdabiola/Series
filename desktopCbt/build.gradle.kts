import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm")
//    kotlin("kapt")
    id("org.jetbrains.compose") version libs.versions.composePlugin


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

    implementation(project(":modules:model"))

    implementation(project(":modules:ui:cbt"))
    implementation(libs.kotlinx.coroutines.swing)
    testImplementation(kotlin("test"))
    testImplementation(project(":modules:testing"))


    implementation(libs.decompose.core)
    implementation(libs.decompose.compose.jetbrains)
    implementation(libs.koin.core)
    implementation(libs.kermit.log)


}

compose.desktop {
    application {
        mainClass = "com.mshdabiola.series.MainCbtAppKt"
        /*
        * Its unreliable. Don't run release tasks for now.
        * Wait until fixed: https://github.com/JetBrains/compose-jb/issues/2393
        */

        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
            obfuscate.set(true)
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
//                https://www.guidgen.com/   generate uuid
                //https://wixtoolset.org/documentation/manual/v3/howtos/general/generate_guids.html
                upgradeUuid = "9ed621bd-5cd6-49d9-90d8-d3ff766709ab"
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
//./gradlew packageUberJarForCurrentOS package
//./gradlew packageUberJarForCurrentOS
//    ./gradlew packageDmg -Pcompose.desktop.mac.sign=true
