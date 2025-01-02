import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.string.shouldContain

class RemoveKotlinTest: StringSpec ({

    "remove Kotlin" {
        val inPutString = """
            Sure, here is an implementation of the given interface:
            
            ```kotlin
            class Context : ForGenerate {

                override fun generate() {
                    // your implementation here
                }
            }
            ```
        """.trimIndent()

        val expectedResult = """
            class Context : ForGenerate {

                override fun generate() {
                    // your implementation here
                }
            }
        """.trimIndent()

        inPutString.removeChatComment() shouldContain expectedResult
    }

})