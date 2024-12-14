import com.code.factory.AllDeclarationFinder
import com.code.factory.Bridge
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.InterfaceFinder
import com.code.factory.ksp.KspProcessor
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import io.kotest.core.spec.style.StringSpec
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk

class KspProcessorTest : StringSpec({

    lateinit var bridge: Bridge
    lateinit var kspProcessor: KspProcessor
    lateinit var codeResolver: CodeResolver
    lateinit var allDeclarationFinder: AllDeclarationFinder
    lateinit var interfaceFinder : InterfaceFinder
    lateinit var resolver: Resolver
    lateinit var types: Sequence<KSClassDeclaration>
    lateinit var declarations: List<KSDeclaration>

    beforeTest {
        bridge = mockk(relaxed = true)
        allDeclarationFinder = mockk()
        resolver = mockk(relaxed = true)
        declarations = listOf()
        every {
            allDeclarationFinder.getAllDeclaration(resolver)
        } returns declarations
        codeResolver = mockk(relaxed = true)
        every {
            codeResolver.getCodeString(*declarations.toTypedArray())
        } returns "code context"
        interfaceFinder = mockk(relaxed = true)
        kspProcessor = KspProcessor(
            logger = mockk(relaxed = true),
            writer = mockk(relaxed = true),
            allDeclarationFinder = mockk(relaxed = true),
            interfaceFinder = interfaceFinder,
            codeResolver =  codeResolver,
            bridge = bridge,
        )
        types = sequenceOf(mockk(relaxed = true))
        every {
            interfaceFinder.getInterfacesWithOutImplementation(resolver)
        } returns types
    }

    "should call InterfaceFinder for getting a types." {
        kspProcessor.process(resolver)
        coVerify { interfaceFinder.getInterfacesWithOutImplementation(resolver) }
    }

    "should call CodeResolver for getting a code." {
        kspProcessor.process(resolver)
        coVerify { codeResolver.getCodeString(*declarations.toTypedArray()) }
    }

    "should call bridge get code" {
        kspProcessor.process(resolver)
        coVerify { bridge.getCode("code context", any()) }
    }

})