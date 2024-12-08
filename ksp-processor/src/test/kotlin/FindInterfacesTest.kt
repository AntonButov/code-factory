import com.code.factory.Bridge
import com.code.factory.ksp.KspProcessor
import com.code.factory.ksp.kspProcessorProvider
import com.code.factory.usescases.getInterfacesWithOutImplementation
import com.tschuchort.compiletesting.KotlinCompilation
import io.mockk.mockk
import utils.compilation
import utils.compilationForAssertations
import kotlin.test.Test
import kotlin.test.assertEquals

class FindInterfacesTest {

    @Test
    fun `simple file should compile`() {
        val bridge = mockk<Bridge>(relaxed = true)
        val source = """
            package test

            class SampleClass {
                fun doSomething() {}
            }
            """

        val result = compilation(source, processorProvider = kspProcessorProvider(bridge)).compile()

        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)
    }

    private val testSource = """
        package somePackage
        
        interface OneInterface {
            fun one()
        }
        
        class OneInterfaceImpl : OneInterface {
            
            override fun one() {
            
            }
         }
        
        interface TwoInterface {
            fun one()
        }
        
    """

    @Test
    fun `should find two interfaces`() {
        compilationForAssertations(testSource) {
            val interfaceNames = getInterfacesWithOutImplementation().map {
                it.qualifiedName!!.getShortName()
            }.toList()
            assertEquals(listOf("TwoInterface"), interfaceNames)
            assertEquals("somePackage", getInterfacesWithOutImplementation().first().packageName.getShortName())
        }
    }
}
