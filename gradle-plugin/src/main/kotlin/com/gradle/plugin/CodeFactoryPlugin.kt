package com.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class CodeFactoryPlugin : Plugin<Project> { // todo may be will delete
    override fun apply(project: Project) {

        project.tasks.register("codeFactoryTask") {

            it.description = "code factory task"

            it.doLast {
                println("Generating file...")

            }
        }
    }
}
