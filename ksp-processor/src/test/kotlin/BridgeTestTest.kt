import com.code.factory.CompileChecker
import com.code.factory.bridge.Bridge
import com.code.factory.bridge.BridgeTestImpl
import com.code.factory.bridge.OpenAiService
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.writer.WriterData
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk

class BridgeTestTest: StringSpec({

    lateinit var bridgeTest: Bridge.BridgeTest
    lateinit var openAi: OpenAiService
    lateinit var storage: Storage
    lateinit var codeResolver: CodeResolver
    lateinit var compileChecker: CompileChecker

    beforeTest {
        openAi = mockk(relaxed = true)
        coEvery { openAi.getCode(any(), any()) } returns """
            ```kotlin
            AiCode
            ```
        """.trimIndent()
        storage = mockk(relaxed = true)
        codeResolver = mockk(relaxed = true)
        compileChecker = mockk(relaxed = true)
        bridgeTest = BridgeTestImpl(
            codeResolver = codeResolver,
            storage = storage,
            openAi = openAi,
            compileChecker = compileChecker
        )
    }

    val writeData = WriterData(
        code = "I am code.",
        packageName = "PackageName",
        name = "Name"
    )

    "when InterfaceFinder find interfaces bridge should to request code resolver for resolve it" {
        every {
            storage.getAllDeclaration()
        } returns "We are declarations."
        every {
            storage.getInterfaceWithOutImplementation()
        } returns writeData
        val writeData = bridgeTest.getCode(mockk(relaxed = true))
        writeData!!.code shouldBe  "AiCode"
    }

    "when allDeclarations is empty code should generate" {
        every {
            storage.getAllDeclaration()
        } returns null
        every {
            storage.getInterfaceWithOutImplementation()
        } returns writeData
        val writeData = bridgeTest.getCode(mockk(relaxed = true))
        writeData!!.code shouldBe  "AiCode"
    }

    "when interfaceWithOutImplementation is null get code should return null" {
        every {
            storage.getAllDeclaration()
        } returns "We are declarations."
        every {
            storage.getInterfaceWithOutImplementation()
        } returns null
        val writeData = bridgeTest.getCode(mockk(relaxed = true))
        writeData shouldBe null
    }

})