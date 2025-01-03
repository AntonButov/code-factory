package com.code.factory

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi
import com.tschuchort.compiletesting.KotlinCompilation.Result as JvmCompilationResult

fun compileChecker(): CompileChecker = CompileCheckerImpl()

interface CompileChecker {
    fun isCompile(files: List<Pair<String, String>>, code: String): Boolean
}

@OptIn(ExperimentalCompilerApi::class)
internal class CompileCheckerImpl: CompileChecker {

    override fun isCompile(files: List<Pair<String, String>>, code: String): Boolean {
        val result = runCatching {  compile(files, code) }.getOrNull() ?: return false
        return result.exitCode == KotlinCompilation.ExitCode.OK
    }

    private fun compile(files: List<Pair<String, String>>, @Language("kotlin") source: String): JvmCompilationResult {
        return KotlinCompilation().apply {
            sources = buildList {
                addAll(files.map { SourceFile.new(it.first, it.second) })
                add(SourceFile.kotlin("main.kt", source))
            }
            messageOutputStream = System.out
            inheritClassPath = true
        }.compile()
    }
}