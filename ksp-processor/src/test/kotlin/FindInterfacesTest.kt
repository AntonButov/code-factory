import com.code.factory.bridge.Bridge
import com.code.factory.compilation.compilationForAssertations
import com.code.factory.interfaceFinder
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

class FindInterfacesTest {

    @Test
    fun `simple file should compile`() {
        val source = """
            package test

            class SampleClass {
                fun doSomething() {}
            }
            """

        compilationForAssertations(source) {}
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
        
        interface InterfaceWithOutImplementation {
            fun one()
        }
        
    """

    @Test
    fun `should find two interfaces`() {
        compilationForAssertations(testSource) { resolver ->
            val interfaceFinder = interfaceFinder()
            val interfaceNames = interfaceFinder.getInterfacesWithOutImplementation(resolver).map {
                it.qualifiedName!!.getShortName()
            }.toList()
            assertEquals(listOf("InterfaceWithOutImplementation"), interfaceNames)
            assertEquals(
                "somePackage",
                interfaceFinder.getInterfacesWithOutImplementation(resolver).first().packageName.getShortName()
            )
        }
    }
}
