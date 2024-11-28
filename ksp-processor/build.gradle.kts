plugins {
    id("com.google.devtools.ksp") version "2.0.20-1.0.25"
    kotlin("jvm")
    id ("maven-publish")
    `java-gradle-plugin`
}

group = "com.code.factory"
version = "0.0.1"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.kotlin.kspApi)
    testImplementation(kotlin("test"))
    testImplementation(libs.tschuchortdev.testing.ksp)
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