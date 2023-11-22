import com.mshdabiola.app.BuildType

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.application")
    id("mshdabiola.android.application.compose")
    id("mshdabiola.android.application.firebase")
    alias(libs.plugins.androidx.baselineprofile)


}

android {
    namespace = "com.mshdabiola.series.editor"

    defaultConfig {
        applicationId = "com.mshdabiola.series.editor"
        testInstrumentationRunner = "com.mshdabiola.testing.InstrumentationTestRunner"
        versionName = "1"
        versionCode = 1
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = BuildType.DEBUG.applicationIdSuffix
            versionNameSuffix = BuildType.DEBUG.versionNameSuffix
        }
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            // To publish on the Play store a private signing key is required, but to allow anyone
            // who clones the code to sign and run the release variant, use the debug signing key.
            // TODO: Abstract the signing configuration to a separate file to avoid hardcoding this.
            // Todo: comment code before release
//            signingConfig = signingConfigs.getByName("debug")
            applicationIdSuffix = BuildType.RELEASE.applicationIdSuffix
            versionNameSuffix = BuildType.RELEASE.versionNameSuffix
        }
        val benchmark by creating {
            // Enable all the optimizations from release build through initWith(release).
            initWith(release)
            matchingFallbacks.add("release")
            // Debug key signing is available to everyone.
//            signingConfig = signingConfigs.getByName("debug")
            // Only use benchmark proguard rules
            proguardFiles("benchmark-rules.pro")
            //  FIXME enabling minification breaks access to demo backend.
            isMinifyEnabled = false
            applicationIdSuffix = BuildType.BENCHMARK.applicationIdSuffix
            versionNameSuffix = BuildType.BENCHMARK.versionNameSuffix
        }
    }


    packagingOptions {
        excludes += "/META-INF/AL2.0"
        excludes += "/META-INF/LGPL2.1"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.profileinstaller)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.timber)

    implementation(project(":modules:model"))

    implementation(libs.koin.core)
    implementation(libs.koin.android)

    //implementation(project(":modules:designsystem"))
    //implementation(project(":modules:navigation:cbt"))
    implementation(project(":modules:ui:editor"))
    implementation(libs.decompose.core)


    androidTestImplementation(project(":modules:testing"))
    "baselineProfile"(project("::androidEditor:baselineprofile"))


}
//
//@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
//plugins {
//    alias(libs.plugins.android.application)
//    id("org.jetbrains.kotlin.android")
//}
//
//android {
//    namespace = "com.mshdabiola.series.editor"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.mshdabiola.series.editor"
//        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary = true
//        }
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        kotlinCompilerExtensionVersion = "1.5.4-dev-k1.9.20-50f08dfa4b4"
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
//}
//
//dependencies {
//
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.activity.compose)
//   // implementation(libs.androidx.profileinstaller)
//    implementation(libs.androidx.core.splashscreen)
//    implementation(libs.timber)
//
//
//    implementation(libs.koin.core)
//    implementation(libs.koin.android)
//    implementation(libs.koin.android.compose)
//
//    implementation(project(":modules:model"))
//    //implementation(project(":modules:navigation:cbt"))
//    implementation(project(":modules:ui:editor"))
//    implementation(libs.decompose.core)
//
//
//    androidTestImplementation(project(":modules:testing"))
//   // "baselineProfile"(project(":app:baselineprofile"))
//
////    implementation(libs.androidx.core.ktx)
////    implementation(libs.androidx.lifecycle.runtime.ktx)
////    implementation(libs.androidx.activity.compose)
////    implementation(platform(libs.androidx.compose.bom))
////    implementation(libs.androidx.ui)
////    implementation(libs.androidx.ui.graphics)
////    implementation(libs.androidx.ui.tooling.preview)
////    implementation(libs.androidx.material3)
////    testImplementation(libs.junit4)
////    androidTestImplementation(libs.androidx.junit)
////    androidTestImplementation(libs.androidx.test.espresso.core)
////    androidTestImplementation(platform(libs.androidx.compose.bom))
////    androidTestImplementation(libs.androidx.compose.ui.test)
////
//    debugImplementation(libs.androidx.compose.ui.tooling)
//    debugImplementation(libs.androidx.compose.ui.testManifest)
//}