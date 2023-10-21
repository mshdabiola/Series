plugins {
    id("mshdabiola.mpp.feature")
}

android {
    namespace = "com.mshdabiola.ui.cbt"

}

kotlin {
    androidTarget()
    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:retex"))
                implementation(project(":modules:model"))
                implementation(project(":modules:ui:common"))
            }
        }


    }
}

