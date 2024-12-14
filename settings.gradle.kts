pluginManagement {

    plugins {
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
        kotlin("jvm") version "2.0.20"
    }

    repositories {
        mavenCentral()
    }

}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "code-factory"

include("bridge")
include("gradle-plugin")
include("ksp-processor")
include("utils")
include("writer")
