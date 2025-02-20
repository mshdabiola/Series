import com.vanniktech.maven.publish.SonatypeHost
import java.util.Properties

plugins {
    id("mshdabiola.jvm.library")
    id("mshdabiola.android.library.publish")

}

mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        artifactId = "jretex",
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Jretex")
        description.set("Jretex KMP Library")
    }
}

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