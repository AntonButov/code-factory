import org.gradle.kotlin.dsl.implementation
import org.gradle.kotlin.dsl.runtimeOnly

plugins {
    id("com.google.devtools.ksp") version "2.0.20-1.0.25"
    kotlin("jvm")
    id ("maven-publish")
    `java-gradle-plugin`
}

group = "com.code.factory"
version = "0.0.1"

dependencies {
    implementation(project(":utils"))
    implementation(project(":bridge"))

    implementation(kotlin("stdlib"))
    implementation(libs.kotlin.kspApi)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(kotlin("test"))
    testImplementation(libs.tschuchortdev.testing.ksp)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner.junit5.jvm)
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

tasks.named("test") {
    dependsOn("publishToMavenLocal")
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["kotlin"])
            artifactId = "code-factory-ksp"
        }
    }

    repositories {
        mavenLocal()
    }
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}