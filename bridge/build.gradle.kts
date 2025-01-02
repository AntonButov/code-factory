plugins {
    kotlin("jvm")
    id ("maven-publish")
}

group = "com.code.factory"
version = "0.0.1"

dependencies {
    implementation(platform(libs.openai.kotlin.bom))
    implementation (libs.openai.kotlin)
    runtimeOnly (libs.ktor)

    testImplementation(kotlin("test"))
    testImplementation(libs.kotest.assertions)
    testImplementation(libs.mockk)
    testImplementation(libs.kotest.runner.junit5.jvm)
}

tasks.named("test") {
    dependsOn("publishToMavenLocal")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["kotlin"])
            artifactId = "code-factory-bridge"
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