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
   // implementation(libs.kotlinPoet)
    implementation(libs.kotlinPoet.ksp)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.kotest.runner.junit5.jvm)
    testImplementation(libs.mockk)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}