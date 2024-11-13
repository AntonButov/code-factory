package com.code.factory.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class Plugin : Plugin<Project> {
        override fun apply(project: Project) {
            project.tasks.register("helloTest") {
                it.description = "Runs a simple test"
                it.doLast {
                    println("Running tests...")
                }
            }
        }
    }
