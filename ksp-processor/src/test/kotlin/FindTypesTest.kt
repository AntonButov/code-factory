import com.code.factory.AllDeclarationFinderImpl
import com.code.factory.compilation.compilationForAssertations
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

        compilationForAssertations(typeASource, mainCode) { resolver ->
            assertEquals(listOf("A"), AllDeclarationFinderImpl().getAllDeclaration(resolver).map { it.toString() })
        }
    }

}