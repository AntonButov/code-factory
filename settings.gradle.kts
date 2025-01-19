pluginManagement {

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
