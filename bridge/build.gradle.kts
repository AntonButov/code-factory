plugins {
    kotlin("jvm")
}

group = "com.code.factory"
version = "1.0-SNAPSHOT"

dependencies {
    implementation(platform(libs.openai.kotlin.bom))

    implementation (libs.openai.kotlin)
    runtimeOnly (libs.ktor)

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