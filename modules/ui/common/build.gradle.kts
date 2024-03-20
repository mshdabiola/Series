plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
}

android {
    namespace = "com.mshdabiola.ui.common"

}

kotlin {

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:retex"))
                implementation(project(":modules:designsystem"))
                implementation(project(":modules:analytics"))

            }
        }


    }
}

