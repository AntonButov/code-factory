import com.code.factory.coderesolver.codeResolver
import com.code.factory.compilation.compilationForAssertations
import com.google.devtools.ksp.getClassDeclarationByName
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.equals.shouldBeEqual
import io.kotest.matchers.string.shouldContain

class CodeResolverTest: StringSpec({

    "Code resolver should return same code in String" {
        val sourceCode = """
            class MyClass {
            
            }
            
        """.trimIndent()
        val codeResolver = codeResolver()
        compilationForAssertations(sourceCode) { resolver ->
            val myClassDeclaration = resolver.getClassDeclarationByName(resolver.getClassDeclarationByName("MyClass")!!.qualifiedName!!)
            "MyClass" shouldBeEqual myClassDeclaration!!.qualifiedName!!.getShortName()
            val resolvedCode = codeResolver.getCodeString(listOf(myClassDeclaration))
            sourceCode shouldContain resolvedCode.first().second
        }
    }
})