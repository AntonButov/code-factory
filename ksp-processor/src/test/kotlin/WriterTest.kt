import com.code.factory.MainCodeWriter
import com.code.factory.writer.StorageWriter
import com.code.factory.writer.WriterData
import com.code.factory.writer.writer
import com.google.devtools.ksp.processing.CodeGenerator
import io.kotest.core.spec.style.StringSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class WriterTest : StringSpec({

    "when writer write code generator should to invoke create file" {
        val storageWriter = mockk<StorageWriter>(relaxed = true)
        every {
            storageWriter.getKotlinPath()
        } returns "Some path"
        val mainCodeWriter = mockk<MainCodeWriter>(relaxed = true)
        val codeGenerator = mockk<CodeGenerator>(relaxed = true)

        val writer = writer(storageWriter, mainCodeWriter, codeGenerator)
        val writeData = WriterData(
            code = "Some code",
            packageName = "SomePackage",
            name = "name"
        )

        writer.write(
            writerData = writeData
        )

        verify {
            mainCodeWriter.write(writeData)
        }
        verify {
            codeGenerator.createNewFile(any(), any(), any())
        }
    }

})