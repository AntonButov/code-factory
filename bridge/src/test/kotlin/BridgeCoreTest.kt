import com.code.factory.Bridge
import com.code.factory.BridgeImpl
import com.code.factory.OpenAiService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk

class BridgeCoreTest: StringSpec({

    lateinit var bridge: Bridge
    lateinit var openAi: OpenAiService

    beforeTest {
        openAi = mockk(relaxed = true)
        coEvery { openAi.getCode(any(), any()) } returns "AiCode"
        bridge = BridgeImpl(
            openAi = openAi
        )
    }

    "when InterfaceFinder find interfaces bridge should to request code resolver for resolve id" {
        val code = bridge.getCode("some code", "interface")
        code shouldBe  "AiCode"
    }

})