package com.code.factory

import com.google.devtools.ksp.processing.KSPLogger
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.intellij.lang.annotations.Language
import org.jetbrains.kotlin.compiler.plugin.ExperimentalCompilerApi

fun compileChecker(logger: KSPLogger): CompileChecker = CompileCheckerImpl()

interface CompileChecker {
    fun checkCompile(context: String, test: String): Boolean
}

@OptIn(ExperimentalCompilerApi::class)
internal class CompileCheckerImpl() : CompileChecker {
    override fun checkCompile(context: String, test: String): Boolean {
        return compile(context, test).exitCode == KotlinCompilation.ExitCode.OK
    }

    private fun compile(@Language("kotlin") context: String, @Language("kotlin") generatedCode: String): KotlinCompilation.Result {
        return KotlinCompilation().apply {
            sources = buildList {
                add(SourceFile.kotlin("Context.kt", context))
                add(SourceFile.kotlin("GeneratedCode.kt", generatedCode))
            }
            messageOutputStream = System.out
            verbose = true
            inheritClassPath = true
        }.compile()
    }
}