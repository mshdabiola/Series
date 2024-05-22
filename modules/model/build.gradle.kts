@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("mshdabiola.android.library")
//    id("maven-publish")
}
//group = "com.mshdabiola.model"
//version = "1.0.0"

//publishing {
//    repositories {
//        maven {
//            //...
//        }
//    }
//}

android {
    namespace = "com.mshdabiola.model"
}

kotlin {
//    kotlin {
//        androidTarget {
//            publishLibraryVariants("release", "debug")
//        }
//    }
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        browser()
//    }
    sourceSets {
        val commonMain by getting {
            dependencies {

            }
        }

        val commonTest by getting {
            dependencies {

            }
        }

        val androidMain by getting {
            dependencies {

            }
        }


        val desktopMain by getting {
            dependencies {

            }
        }

        val desktopTest by getting

//        val jsMain by getting {
//            dependencies {
//
//            }
//        }
    }
}

//plugins {
//    id("java-library")
//    id("org.jetbrains.kotlin.jvm")
//}
//
//java {
//    sourceCompatibility = JavaVersion.VERSION_17
//    targetCompatibility = JavaVersion.VERSION_17
//}
//tasks.test {
//    useJUnitPlatform()
//}
//dependencies {
//    // Other dependencies.
//    testImplementation(kotlin("test"))
//}
//tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
//    kotlinOptions {
//        jvmTarget = "17"
//    }
//}