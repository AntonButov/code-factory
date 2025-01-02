plugins {
    alias(libs.plugins.ksp)
    kotlin("jvm")
}
repositories {
    mavenCentral()
    google()
}

version = "1.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib"))
    ksp(project(":ksp-processor"))
 //   kspTest(project(":ksp-processor"))

    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner.junit5.jvm)
}

ksp {
    arg("localPropertiesPath", rootDir.path)
}