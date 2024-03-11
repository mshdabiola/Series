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
        maven(url = "https://androidx.dev/storage/compose-compiler/repository/")

    }
}
rootProject.name = "Series"
include("cbtApp")
include(":editorApp")
include(":modules:database")
include(":modules:designsystem")
include(":modules:model")
include(":modules:analytics")

include(":modules:data:cbt")
include(":modules:data:editor")
include(":modules:domain:cbt")
include(":modules:domain:editor")
include(":modules:testing")
include(":modules:ui:cbt")
include(":modules:ui:editor")
//include(":desktopEditor")
//include(":desktopCbt")

include(":modules:setting:cbt")
include(":modules:setting:editor")
include(":modules:navigation:cbt")
include(":modules:navigation:editor")

include(":modules:app:cbt")
include(":modules:app:editor")
include(":modules:retex")
include(":modules:jretex")
//include(":modules:svgtovector")
include(":modules:ui:common")
include(":modules:mvvn")

//include(":androidCbt:baselineprofile")
//include(":androidEditor")
//include(":androidEditor:baselineprofile")

