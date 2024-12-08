import com.code.factory.AllDeclarationFinderImpl
import utils.compilationForAssertations
import kotlin.test.Test
import kotlin.test.assertEquals

class FindTypesTest {

    @Test
    fun `when contain A-type should find it`() {
        val typeASource = """
           class A {
           }
       """.trimIndent()

        val mainCode = """
            
            fun someFun() {
                val classA: A = A()
            }
            
        """.trimIndent()

        compilationForAssertations(typeASource, mainCode) {
            assertEquals(listOf("A"), AllDeclarationFinderImpl().getAllDeclaration(this).map { it.toString() })
        }
    }

}