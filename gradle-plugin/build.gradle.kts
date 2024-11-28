plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    id ("maven-publish")
}

group = "com.gradle.plugin"
version = "1.0-SNAPSHOT"

gradlePlugin {
    plugins {
        create("graldePlugin") {
            id = "com.gradle.plugin"
            implementationClass = "com.gradle.plugin.CodeFactoryPlugin"
        }
    }
}

tasks.named("test") {
    dependsOn("publishToMavenLocal")
}

dependencies {
    testImplementation(kotlin("test"))

    implementation(kotlin("stdlib"))
    implementation("com.github.tschuchortdev:kotlin-compile-testing:1.5.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}