plugins {
    id("mshdabiola.android.feature")
}

android {
    namespace = "com.mshdabiola.mainscreen"
    buildTypes {
        create("benchmark") {
            matchingFallbacks += listOf("release")
        }
    }
}
dependencies {

//    implementation(project(":common:data"))
}