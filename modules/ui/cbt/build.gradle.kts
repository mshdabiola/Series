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
                implementation(project(":modules:mvvn"))
                implementation(project(":modules:data:cbt"))

                implementation(project(":modules:navigation:cbt"))
                implementation(project(":modules:retex"))
                implementation(libs.koin.core)

                implementation(libs.decompose.core)
                implementation(libs.decompose.compose.jetbrains)
            }
        }
        val androidMain by getting {
            dependencies {



//
//                implementation(libs.koin.android)
//                implementation(libs.koin.android.compose)

             //   implementation(libs.androidx.core.ktx)




//                implementation(libs.androidx.lifecycle.runtimeCompose)


            }
        }


    }
}

