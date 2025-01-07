import com.code.factory.CompileChecker
import com.code.factory.compileChecker
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class CheckCompilerTest: StringSpec ({

    lateinit var checker: CompileChecker

    beforeTest {
        checker = compileChecker()
    }

    val contextClass = """
            package com.gradle.plugin
            
            class SomeClass() {
                fun someFun() {
                    println("do work")
                }
            }
                     """.trimIndent()

    "Hello word should compile" {
        val generatedClass = """
            package com.gradle.plugin

            class Main {
                fun main() {
               println("Hello World!")
           }
        }
        """.trimIndent()

        checker.isCompile(listOf("FileOfContext.kt" to contextClass), generatedClass) shouldBe true
    }

    "when context define interface and gen code it implement all code should compile" {
        val contextInterface = """
            interface MyInterface {
                fun myFun()
            }
        """.trimIndent()

        val generatedClass = """
            class NewClass: MyInterface {
                override fun myFun() {
                    // I work
                }
            }
        """.trimIndent()
        checker.isCompile(listOf("FileOfContext.kt" to contextInterface), generatedClass) shouldBe true
    }

    "error compile test" {
        val testClass = """
           I am a error
        
        """.trimIndent()

        checker.isCompile(listOf("FileOfContext.kt" to contextClass), testClass) shouldBe false
    }

})