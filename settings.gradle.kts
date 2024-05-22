pluginManagement {
    repositories {
        includeBuild("build-logic")
        // maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        mavenCentral()
        mavenLocal()
        gradlePluginPortal()
    }
}
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        mavenLocal()

        maven(url = "https://www.jitpack.io")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
        maven(url = "https://androidx.dev/storage/compose-compiler/repository/")
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
}
rootProject.name = "series"
//include(":app")
//include(":app:baselineprofile")
include(":modules:database")
include(":modules:model")
include(":modules:network")
include(":modules:testing")
include(":modules:retex")
include(":modules:jretex")
include(":modules:datastore")


include(":modules:analytics")

include(":modules:ui")
include(":modules:designsystem")



include(":editorApp")


