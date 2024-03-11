import com.mshdabiola.app.configureFlavors

plugins {
    id("mshdabiola.android.library")
    id("mshdabiola.android.library.compose")
}

android {
    namespace = "com.mshdabiola.ui.cbt"
//    configureFlavors(this)

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
                implementation(project(":modules:designsystem"))

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
                implementation(libs.koin.android)
                implementation(libs.koin.android.compose)

                implementation(libs.play.update.kts)
                implementation(libs.play.update)

                implementation(libs.play.review.kts)
                implementation(libs.play.review)


                implementation(libs.play.game)
                implementation(libs.play.coroutine)


            }
        }


    }
}

