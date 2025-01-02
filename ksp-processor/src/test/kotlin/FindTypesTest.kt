import com.code.factory.AllDeclarationFinderImpl
import com.code.factory.coderesolver.codeResolver
import com.code.factory.compilation.compilationForAssertations
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import kotlin.test.assertEquals

class FindTypesTest : StringSpec({

    "when contain A-type should find it" {
        val typeASource = """
           class A {
           }
       """.trimIndent()

        val mainCode = """
            
            fun someFun() {
                val classA: A = A()
            }
            
        """.trimIndent()

        compilationForAssertations(typeASource, mainCode) { resolver ->
            assertEquals(listOf("A"), AllDeclarationFinderImpl().getAllDeclaration(resolver).map { it.toString() })
        }
    }

    "when work with simple test should return test class" {
        val codeResolver = codeResolver()
        val testClass = """
            import java.io.File
            import kotlin.test.Test
            import kotlin.test.assertEquals

            class GeneratedTest {

                @Test
                fun generatedFileGets() {
                    val generatedFile: File = File("integration-test/build/generated/ksp/main/kotlin/ForGenerate.kt") //#45
                    assertEquals(""${'"'}
                        class GeneratedCode(): ForGenerate {

                        }
                    ""${'"'}.trimIndent(), generatedFile.readText())
                }
            }
        """.trimIndent()
        compilationForAssertations(testClass) { resolver ->
            val allDeclarations = AllDeclarationFinderImpl().getAllDeclaration(resolver)
            val code = codeResolver.getCodeString(allDeclarations).toMap()
            code[allDeclarations.last()] shouldBe testClass
        }
    }
})