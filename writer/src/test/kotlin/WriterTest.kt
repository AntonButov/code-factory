import com.code.factory.writer
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class WriterTest : StringSpec({

    val codeGenerator: CodeGenerator = mockk(relaxed = true)
    val ksDeclaration: KSClassDeclaration = mockk(relaxed = true)
    val writer = writer(codeGenerator)

    "when writer write code generator should to invoke create file" {
        every { ksDeclaration.packageName.asString() } returns "MockedDeclaration"
        writer.write("some code", ksDeclaration)
        verify {
            codeGenerator.createNewFile(
                dependencies = any(),
                packageName = any(),
                fileName = any()
            )
        }
    }
})