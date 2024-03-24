plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
}

android {
    namespace = "com.mshdabiola.ui.editor"

}

kotlin {
    androidTarget()
    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:retex"))
                api(project(":modules:ui:common"))

                implementation(project(":modules:model"))
                implementation(project(":modules:mvvn"))
                implementation(project(":modules:data:editor"))
                implementation(project(":modules:designsystem"))

                implementation(project(":modules:navigation:editor"))
                implementation(libs.koin.core)
                api(libs.calf.filepicker)

                implementation(libs.decompose.core)
                implementation(libs.decompose.compose.jetbrains)
            }
        }

        val androidMain by getting {
            dependencies {

                implementation(libs.androidx.activity.compose)

                implementation(libs.android.svg.arr)
                implementation(libs.koin.android)
                implementation(libs.koin.android.compose)

            }
        }
//
//        val desktopMain by getting{
//            dependencies {
//
//                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
//                implementation(compose.desktop.components.splitPane)
//            }
//
//        }


    }
}

