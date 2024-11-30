package utils

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated

class TestKspProcessor(
    val logger: KSPLogger, // for research
    val assertAction: Resolver.() -> Unit
): SymbolProcessor {
    override fun process(resolver: Resolver): List<KSAnnotated> {
        assertAction(resolver)
        return emptyList()
    }

    companion object {
        fun provider(assertAction: Resolver.() -> Unit): SymbolProcessorProvider {
            return object : SymbolProcessorProvider {
                override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
                    return TestKspProcessor(environment.logger, assertAction)
                }
            }
        }
    }
}