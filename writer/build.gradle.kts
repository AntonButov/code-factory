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

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}