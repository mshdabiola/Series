plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
    //  id("mshdabiola.android.hilt")
}

android {
    namespace = "com.mshdabiola.app"
}
dependencies {


    implementation(project(":modules:navigation"))
    implementation(project(":feature:mainscreen"))
    implementation(project(":feature:finishscreen"))
    implementation(project(":feature:profilescreen"))
    implementation(project(":feature:questionscreen"))
    implementation(project(":feature:statisticsscreen"))

    implementation(libs.decompose.core)

    implementation(libs.decompose.compose.jetbrains)
    implementation(libs.koin.core)

}