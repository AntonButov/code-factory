import com.code.factory.File
import com.code.factory.compilation.compilationForAssertations
import com.code.factory.writer.writer
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

class WriterTest : StringSpec({

    "when writer write code generator should to invoke create file" {
        val codeGenerator: CodeGenerator = mockk(relaxed = true)
        val ksDeclaration: KSClassDeclaration = mockk(relaxed = true)
        val writer = writer(codeGenerator)

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

    "when write result should match" {
        val ksDeclaration: KSClassDeclaration = mockk(relaxed = true)
        every { ksDeclaration.packageName.asString() } returns "packageForWriting"
        val codeForWriting = """
            class MyClass() : MyInterface {
                override fun myFun() {
                    // work
                }
            }
        """.trimIndent()
        val codeInterface = """
            interface MyInterface {
                fun myFun()
            }
            
        """.trimIndent()
        compilationForAssertations(codeInterface) { resolver ->
            val newFile = resolver.getNewFiles().toList().first()
            when (newFile.fileName) {
                "SomeClass0.kt" -> {
                    val writer = writer(codeGenerator)
                    val myInterface = resolver.getClassDeclarationByName(resolver.getKSNameFromString("MyInterface"))!!
                    writer.write(codeForWriting, myInterface)
                }

                "MyInterface.kt" -> codeForWriting shouldBeEqual File.fileCode(newFile.filePath)
            }
        }
    }
})