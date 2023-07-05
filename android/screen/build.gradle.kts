plugins {
    id("mshdabiola.android.feature")
}

android {
    namespace = "com.mshdabiola.screen"
    buildTypes {
        create("benchmark") {
            matchingFallbacks += listOf("release")
        }
    }
}
dependencies {
    implementation(project(":modules:navigation"))
    implementation(libs.decompose.core)

    implementation(libs.decompose.compose.jetbrains)
    implementation(project(":modules:retex"))
}