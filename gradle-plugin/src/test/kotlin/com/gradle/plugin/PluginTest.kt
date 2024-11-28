package com.gradle.plugin

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertNotNull
import kotlin.test.Test

class PluginTest {

    private val codeFactoryTask = "codeFactoryTask"

    @Test
    fun `plugin task test`() {
        val project: Project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.gradle.plugin")
        project.tasks.forEach { println(it.name) }
        assertNotNull(project.tasks.findByName(codeFactoryTask))
    }
}