import com.code.factory.AllDeclarationFinder
import com.code.factory.AllDeclarationFinderImpl
import com.code.factory.allDeclarationFinder
import com.code.factory.coderesolver.CodeResolver
import com.code.factory.coderesolver.codeResolver
import com.code.factory.compilation.compilationForAssertations
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe
import kotlin.test.assertEquals

private lateinit var codeResolver: CodeResolver
private lateinit var allDeclarationFinder: AllDeclarationFinder

class FindTypesTest : StringSpec({

    beforeTest {
        codeResolver = codeResolver()
        allDeclarationFinder = allDeclarationFinder()
    }

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
            assertEquals(listOf("A"), allDeclarationFinder.getAllDeclaration(resolver).map { it.toString() })
        }
    }

    "when work with simple test should return test class" {
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
            val allDeclarations = allDeclarationFinder.getAllDeclaration(resolver)
            val code = codeResolver.getCodeString(allDeclarations)
            code shouldContain testClass
        }
    }

    "interface in single file should bee founded" {
        val singleInterface = """
            interface SingleInterface {
                fun myFun()
            }
        """.trimIndent()
        compilationForAssertations(singleInterface) { resolver ->
            val allDeclarations = allDeclarationFinder.getAllDeclaration(resolver)
            listOf("SingleInterface") shouldBe allDeclarations.map { it.toString() }
        }
    }
})