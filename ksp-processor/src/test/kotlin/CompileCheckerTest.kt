import com.code.factory.CompileChecker
import com.code.factory.compileChecker
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class CheckCompilerTest : StringSpec({

    lateinit var checker: CompileChecker

    beforeTest {
        checker = compileChecker(mockk(relaxed = true))
    }

    "for generation should compile" {
        val contextClass = """
            class Context {

            }
 
        interface ForGenerate
        """.trimIndent()

        val generated = """
            class GeneratedCode(): ForGenerate {

            }
        """.trimIndent()

        checker.checkCompile(contextClass, generated) shouldBe true
    }

    "when context define interface and gen code it implement all code should compile" {
        val contextInterface = """
            interface MyInterface {
                fun myFun()
            }
        """.trimIndent()

        val generatedClass = """
            class NewClass: MyInterface {
                override fun myFun() {
                    // I work
                }
            }
        """.trimIndent()
        checker.checkCompile(contextInterface, generatedClass) shouldBe true
    }

    "error compile test" {
        val testClass = """
           I am a error
        
        """.trimIndent()

        checker.checkCompile("", testClass) shouldBe false
    }
})