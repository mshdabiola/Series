plugins {
    id("mshdabiola.mpp.feature")
}

android {
    namespace = "com.mshdabiola.retex"

}

kotlin {
    androidTarget()
    jvm("desktop")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:jretex"))

            }
        }


    }
}

