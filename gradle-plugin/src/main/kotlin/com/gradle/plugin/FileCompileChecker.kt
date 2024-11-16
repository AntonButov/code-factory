package com.gradle.plugin

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.intellij.lang.annotations.Language
import com.tschuchort.compiletesting.KotlinCompilation.Result as JvmCompilationResult

class FileCompileChecker {

    fun isFileCompile(code: String): Boolean {
       val result = compile(code)
        return result.exitCode == KotlinCompilation.ExitCode.OK
    }

    private fun compile(@Language("kotlin") source: String): JvmCompilationResult {
        return KotlinCompilation().apply {
            sources = listOf(SourceFile.kotlin("main.kt", source))
            messageOutputStream = System.out
            inheritClassPath = true
        }.compile()
    }
}