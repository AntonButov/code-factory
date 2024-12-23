import com.aallam.openai.api.chat.ChatCompletion
import com.aallam.openai.client.OpenAI
import com.code.factory.bridge.OpenAiServiceImpl
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest

class OpenAiTest : StringSpec({
    runTest {
        "when get code invoked the call should to delegate to OpenAi" {
            val chatCompletion = mockk<ChatCompletion>().apply {
                every { choices } returns kotlin.collections.listOf(mockk(relaxed = true))
            }
            val openAi = mockk<OpenAI>().apply {
                coEvery { chatCompletion(any()) } returns chatCompletion
            }
            val openAiService = OpenAiServiceImpl(openAi)
            openAiService.getCode("context", "interface")
            coVerify { openAi.chatCompletion(any()) }
        }
    }
})

