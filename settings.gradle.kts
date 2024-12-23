pluginManagement {

    plugins {
        kotlin("jvm") version "1.9.24"
    }

    repositories {
        mavenCentral()
    }

}

@file:Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "code-factory"

include("ksp-processor")
include("utils")
include("integration-test")
