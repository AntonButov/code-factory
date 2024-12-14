import com.code.factory.coderesolver.codeResolver
import com.google.devtools.ksp.getClassDeclarationByName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain
import utils.compilationForAssertations

class CodeResolverTest: StringSpec({

    "Code resolver should return same code in String" {
        val sourceCode = """
            class MyClass {
            
            }
            
        """.trimIndent()
        val codeResolver = codeResolver()
        compilationForAssertations(sourceCode) {
            val myClassDeclaration = getClassDeclarationByName(getClassDeclarationByName("MyClass")!!.qualifiedName!!)
            "MyClass" shouldBeEqual myClassDeclaration!!.qualifiedName!!.getShortName()
            val resolvedCode = codeResolver.getCodeString(myClassDeclaration)
            sourceCode shouldContain resolvedCode
        }
    }
})