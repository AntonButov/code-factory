plugins {
    id("com.google.devtools.ksp") version "2.1.0-1.0.29"
    kotlin("jvm") version "2.1.0"
}
repositories {
    mavenLocal()
    mavenCentral()
    google()
}

version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib", "2.1.0"))
    ksp(project(":ksp-processor"))

    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner.junit5.jvm)
}

ksp {
    arg("localPropertiesPath", rootDir.path)
}