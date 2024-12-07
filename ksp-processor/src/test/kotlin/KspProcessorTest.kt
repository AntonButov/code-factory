import com.code.factory.Bridge
import com.code.factory.ksp.KspProcessor
import io.kotest.core.spec.style.StringSpec
import io.mockk.coVerify
import io.mockk.mockk

class KspProcessorTest : StringSpec({

    "should call bridge get code" {
        val bridge: Bridge = mockk(relaxed = true)
        KspProcessor(
            codeGenerator = mockk(relaxed = true),
            logger = mockk(relaxed = true),
            bridge = bridge
        ).process(mockk(relaxed = true))

        coVerify { bridge.getCode(any(), any()) }
    }

})