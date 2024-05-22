plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("maven-publish")
}
group = "com.mshdabiola.series"
version = "0.0.1"

publishing{
    publications {
        create<MavenPublication>("maven"){
            groupId="com.mshdabiola.series"
            artifactId="jretex"
            version="0.0.1"
            from(components["java"])
        }
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