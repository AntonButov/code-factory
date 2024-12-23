import com.code.factory.bridge.Bridge
import com.code.factory.bridge.BridgeImpl
import com.code.factory.bridge.OpenAiService
import com.google.devtools.ksp.processing.KSPLogger
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class BridgeCoreTest: StringSpec({

    lateinit var bridge: Bridge
    lateinit var openAi: OpenAiService
    lateinit var logger: KSPLogger

    beforeTest {
        openAi = mockk(relaxed = true)
        logger = mockk(relaxed = true)
        coEvery { openAi.getCode(any(), any()) } returns "AiCode"
        bridge = BridgeImpl(
            openAi = openAi,
            logger = logger
        )
    }

    "when InterfaceFinder find interfaces bridge should to request code resolver for resolve id" {
        val code = bridge.getCode(mockk(relaxed = true), "interface")
        code shouldBe  "AiCode"
    }

})