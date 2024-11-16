package com.gradle.plugin

import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class CheckCompilerTest {

    private lateinit var checker: FileCompileChecker

    @BeforeTest
    fun setUp() {
        checker = FileCompileChecker()
    }

    @Test
    fun `Hello word should compile`() {
        val testClass = """
            package com.gradle.plugin

            class Main {
                fun main() {
               println("Hello World!")
           }
        }
        """.trimIndent()

        assertTrue { checker.isFileCompile(testClass) }
    }
}