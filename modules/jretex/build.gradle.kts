import java.util.Properties

plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.vanniktech.maven.publish") version "0.29.0"

}
publishing {
    repositories {
        maven {
            name = "githubPackages"
            url = uri("https://maven.pkg.github.com/mshdabiola/series")
            credentials(PasswordCredentials::class)

        }
    }
}

mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        groupId =  libs.versions.groupId.get(),
        artifactId = "jretex",
        version = libs.versions.versionName.get()
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("J retext KMP Library")
        description.set("Sample Kotlin MultiPlatform Library Test")
        inceptionYear.set("2024")
        url.set("https://github.com/mshdabiola/series")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        // Specify developers information
        developers {
            developer {
                id.set("mshdabiola")
                name.set("Lawal abiola")
                email.set("mshdabiola@gmail.com")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/mshdabiola/series")
        }
    }
}
//group = "com.mshdabiola.series"
//version = libs.versions.versionName

var project : Properties?=null
//try {
//    project=  File(rootDir, "local.properties").inputStream().use {
//        Properties().apply { load(it) }
//
//    }
//    // println("user ${ project?.getProperty("gpr.password")}")
//
//
//} catch (e: Exception) {
//
//    //e.printStackTrace()
//}

//publishing{
//    publications {
//        create<MavenPublication>("maven"){
//            groupId="com.mshdabiola.series"
//            artifactId="jretex"
//            version= libs.versions.versionName.get()
//            from(components["java"])
//        }
//    }
//    repositories {
//        maven {
//            name = "library"
//            url = uri("https://maven.pkg.github.com/mshdabiola/series")
//            credentials {
//                username = project?.getProperty("gpr.userid")  ?: System.getenv("USERID")
//                password = project?.getProperty("gpr.password") ?: System.getenv("PASSWORD")
//            }
//        }
//    }
//}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.test {
    useJUnitPlatform()
}
dependencies {
    // Other dependencies.
    testImplementation(kotlin("test"))
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}