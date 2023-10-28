plugins {
    id("mshdabiola.mpp.library")
    alias(libs.plugins.secrets)
    id("app.cash.sqldelight") version "2.0.0"
}
secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.defaults.properties"
}
sqldelight {

    databases {

        create("SeriesDatabase") {
            packageName.set("com.mshdabiola.database")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/com.mshdabiola.database/files"))
            //dialect("sqlite:3.38")
        }
    }

}

android {
    namespace = "com.mshdabiola.database"
    buildFeatures {
        buildConfig = true
    }
}
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":modules:model"))

                // implementation(libs.koin.core)
                //   implementation(libs.kermit.log)
                implementation(libs.sqldelight.runtime)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.sqldelight.primitive.adapter)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.sqldelight.sqlite.driver)
            }
        }

        val androidMain by getting {
            dependencies {
                //implementation(libs.androidx.compose.ui)
               //0oooo0 implementation(libs.sqldelight.sqlite.driver)
                implementation(libs.sqldelight.android.driver)
                implementation("androidx.sqlite:sqlite-framework:2.3.1")
            }
        }


        val desktopMain by getting {
            dependencies {
                api(libs.sqldelight.sqlite.driver)
            }
        }
    }
}
