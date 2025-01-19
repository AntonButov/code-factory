plugins {
    alias(libs.plugins.ksp)
    kotlin("jvm")
    alias(libs.plugins.kotlin.seialization)
    id("maven-publish")
    `java-gradle-plugin`
}

group = "com.code.factory"
version = "0.0.1"

dependencies {
    ksp(libs.autoservice.ksp)
    testImplementation(project(":utils"))

    implementation(platform(libs.openai.kotlin.bom))
    implementation(libs.openai.client)
    runtimeOnly(libs.ktor.okhttp)

    implementation(kotlin("stdlib"))
    implementation(libs.kotlin.kspApi)
    implementation(libs.autoservice.annotations)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.tschuchortdev.testing.ksp)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(kotlin("test"))
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
ksp {
    arg("localPropertiesPath", rootDir.path)
}