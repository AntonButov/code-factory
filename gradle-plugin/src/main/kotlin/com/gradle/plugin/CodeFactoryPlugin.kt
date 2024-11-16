package com.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.io.File

class CodeFactoryPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.tasks.register("codeFactoryTask") {
            it.description = "code factory task"
            it.doLast {
                println("Generating file...")

                // Создаем директорию
                val outputDir = File(project.buildDir, "generated")
                outputDir.mkdirs()

                // Создаем и записываем файл с текстом
                val outputFile = File(outputDir, "generated.txt")
                val content = """
                    This is a generated file.
                    Hello from CodeFactoryPlugin!
                """.trimIndent()
                outputFile.writeText(content)

                println("File generated at: ${outputFile.absolutePath}")
            }
        }
    }
}
