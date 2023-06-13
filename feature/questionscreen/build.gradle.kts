plugins {
    id("mshdabiola.android.feature")
}

android {
    namespace = "com.mshdabiola.questionscreen"
    buildTypes {
        create("benchmark") {
            matchingFallbacks += listOf("release")
        }
    }
}
dependencies {

//    implementation(project(":common:data"))
}