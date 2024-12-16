plugins {
    kotlin("jvm")
}

group = "com.code.factory"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.kspApi)
    implementation(kotlin("stdlib"))
    implementation(libs.tschuchortdev.testing.ksp)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner.junit5.jvm)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}