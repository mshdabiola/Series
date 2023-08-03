@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.mpp.library")
    id("mshdabiola.mpp.library.compose")
}

android {
    namespace = "com.mshdabiola.retex"
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:jretex"))
            }
        }
    }

}
