import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class GeneratedTest {

    @Test
    fun generatedFileGets() {
        val generatedFile: File = File("build/generated/ksp/main/kotlin/ForGenerate.kt") // #45
        assertEquals("""
            class GeneratedCode(): ForGenerate {

            }
        """.trimIndent(), generatedFile.readText())
    }
}