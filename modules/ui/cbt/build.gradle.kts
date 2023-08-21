plugins {
    //kotlin("multiplatform")
    id("mshdabiola.mpp.library")
    id("mshdabiola.mpp.library.compose")
}

android {
    namespace = "com.mshdabiola.ui.cbt"
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(project(":modules:retex"))
                implementation(project(":modules:model"))
            }
        }

    }
}