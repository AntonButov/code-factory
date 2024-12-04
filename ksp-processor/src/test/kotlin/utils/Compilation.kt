package utils

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders
import kotlin.test.assertEquals


fun compilationForAssertations(
    vararg sources: String,
    assertAction: Resolver.() -> Unit
) {
    val testKspProcessorProvider = TestKspProcessor.provider(assertAction)
    compilation(sourceFiles = sources.toList().toSomeClasses(), processorProvider = testKspProcessorProvider)
        .compile()
        .also {
            if (KotlinCompilation.ExitCode.OK == it.exitCode) return
            error(it.messages)
        }
}

private fun List<String>.toSomeClasses(): List<SourceFile> = mapIndexed { index, name -> name.toSomeClass(index) }

private fun String.toSomeClass(index: Int) = SourceFile.kotlin("SomeClass$index.kt", this)

fun compilation(
    vararg sources: String,
    processorProvider: SymbolProcessorProvider
) = compilation(sources.toList().toSomeClasses(), processorProvider)

fun compilation(sourceFiles: List<SourceFile>, processorProvider: SymbolProcessorProvider) = KotlinCompilation().apply {
    sources = sourceFiles
    symbolProcessorProviders = listOf(processorProvider)
    inheritClassPath = true
}