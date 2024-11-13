plugins {
    kotlin("jvm") version "2.0.20"
    `java-gradle-plugin`
}

group = "com.code.factory"
version = "1.0.0"

gradlePlugin {
    plugins {
        create("graldePlugin") {
            id = "com.code.factory.plugin"
            implementationClass = "com.code.factory.gradle.plugin.Plugin"
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