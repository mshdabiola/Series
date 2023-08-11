pluginManagement {
    repositories {
        includeBuild("build-logic")
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://www.jitpack.io")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven(url = "https://maven.pkg.jetbrains.space/kotlin/p/kotlin/kotlin-js-wrappers")
        maven(url = "https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
    }
}
rootProject.name = "Series"
//include(":template:app")
//include(":template:benchmark")
include(":app")
//include(":physics:benchmark")
include(":modules:database")
include(":modules:designsystem")
include(":modules:model")
//include(":modules:network")
include(":modules:data")
include(":modules:domain")
include(":modules:testing")
include(":modules:ui")
//include(":android:worker")
include(":desktop")
include(":modules:setting")
include(":modules:navigation")
include(":modules:retex")
include(":modules:jretex")
include(":modules:util")
//include(":android:screen")
