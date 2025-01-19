import com.code.factory.AllDeclarationFinder
import com.code.factory.CompileChecker
import com.code.factory.InterfaceFinder
import com.code.factory.bridge.BridgeFactory
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.ksp.KspProcessor
import com.code.factory.ksp.PhaseResolver
import com.code.factory.ksp.Phases
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class KspProcessorTest : StringSpec({

    lateinit var bridgeFactory: BridgeFactory
    lateinit var kspProcessor: KspProcessor
    lateinit var codeResolver: CodeResolver
    lateinit var allDeclarationFinder: AllDeclarationFinder
    lateinit var interfaceFinder: InterfaceFinder
    lateinit var resolver: Resolver
    lateinit var types: Sequence<KSClassDeclaration>
    lateinit var declaration: KSDeclaration
    lateinit var declarations: List<KSDeclaration>
    lateinit var compileChecker: CompileChecker
    lateinit var phaseResolver: PhaseResolver

    beforeTest {
        bridgeFactory = mockk(relaxed = true)
        allDeclarationFinder = mockk()
        resolver = mockk(relaxed = true)
        declaration = mockk<KSDeclaration>(relaxed = true)
        declarations = listOf(declaration)
        every {
            allDeclarationFinder.getAllDeclaration(any())
        } returns declarations
        codeResolver = mockk(relaxed = true)
        every {
            codeResolver.getCodeString(declarations)
        } returns listOf("code context")
        interfaceFinder = mockk(relaxed = true)
        compileChecker = mockk(relaxed = true)
        phaseResolver = mockk(relaxed = true)
        kspProcessor = KspProcessor(
            logger = mockk(relaxed = true),
            writer = mockk(relaxed = true),
            allDeclarationFinder = allDeclarationFinder,
            interfaceFinder = interfaceFinder,
            codeResolver = codeResolver,
            bridgeFactory = bridgeFactory,
            compileChecker = compileChecker,
            phaseResolver = phaseResolver,
        )
    }

    "when phase main should work bridge main" {
        every {
            phaseResolver.resolvePhase(any())
        } returns Phases.Main
        kspProcessor.process(resolver)

        verify {
            bridgeFactory.createMain()
        }
    }

    "when phase test should work bridge test" {
        every {
            phaseResolver.resolvePhase(any())
        } returns Phases.Tests
        kspProcessor.process(resolver)

        verify {
            bridgeFactory.createTest()
        }
    }
})