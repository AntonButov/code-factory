import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessorProviders


fun compilationForAssertations(
    source: String,
    assertAction: Resolver.() -> Unit
) {
    val testKspProcessorProvider = TestKspProcessor.provider(assertAction)
    compilation(sourceFile = source.toSomeClass(), processorProvider = testKspProcessorProvider)
}

private fun String.toSomeClass() = SourceFile.kotlin("SomeClass.kt", this)

fun compilation(source: String, processorProvider: SymbolProcessorProvider) =
    compilation(source.toSomeClass(), processorProvider)

fun compilation(sourceFile: SourceFile, processorProvider: SymbolProcessorProvider) =
    compilation(listOf(sourceFile), processorProvider)

fun compilation(sourceFiles: List<SourceFile>, processorProvider: SymbolProcessorProvider) = KotlinCompilation().apply {
    sources = sourceFiles
    symbolProcessorProviders = listOf(processorProvider)
    inheritClassPath = true
}