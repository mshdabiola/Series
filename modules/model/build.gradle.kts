@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    alias(libs.plugins.kotlin.serialization)
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
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.android.tools:common:31.1.0")
    testImplementation(kotlin("test"))
    implementation(libs.kotlinx.serialization.json)
}
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}