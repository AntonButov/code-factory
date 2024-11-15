plugins {
    kotlin("jvm") version "2.0.20"
    `java-gradle-plugin`
}

group = "com.gradle.plugin"
version = "1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        create("graldePlugin") {
            id = "com.gradle.plugin"
            implementationClass = "com.gradle.plugin.CodeFactoryPlugin"
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}