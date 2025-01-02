import kotlin.test.Test
import kotlin.test.assertEquals

class GeneratedTest {

    @Test
    fun generatedFileGets() {
       val forGenerate: ForGenerate = GeneratedCode()
       val expected = forGenerate.plus(2, 2)
       assertEquals(4, expected)
    }
}