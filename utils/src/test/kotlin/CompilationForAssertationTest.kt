import com.code.factory.compilation.compilationForAssertations
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.test.assertTrue

class CompilationForAssertationTest {

    @Test
    fun `correct code should compile`() {
        val correctCode = """
            class Test {
                fun test() {
                    return "test"
                }
            }
        """.trimIndent()
        compilationForAssertations(correctCode) {
            assertTrue { true }
        }
    }

    @Test
    fun `incorrect code should not compile`() {
        assertThrows<IllegalStateException> {
            compilationForAssertations("") {
                error("My error.")
            }
        }
    }
}