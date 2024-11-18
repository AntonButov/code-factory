import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.gradle.util.internal.GFileUtils.writeFile
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.BeforeTest
import kotlin.test.assertEquals



class KspProcessorPluginTest {

    @TempDir
    var testProjectDir: File? = null
    private var settingsFile: File? = null
    private var buildFile: File? = null

    @BeforeTest
    fun setup() {
        setupGradle()
    }

    private fun setupGradle() {
        settingsFile = File(testProjectDir, "settings.gradle.kts")
        writeFile(
            """
            pluginManagement {
            plugins {
                id("com.google.devtools.ksp") version "2.0.20-1.0.25"
                kotlin("jvm") version "2.0.20"
                id("com.gradle.plugin") version "1.0-SNAPSHOT"
            }
            repositories {
                mavenLocal()
                mavenCentral()
                gradlePluginPortal()
                }
            }
            rootProject.name = "test-project"
        """,
            settingsFile
        )

        buildFile = File(testProjectDir, "build.gradle.kts")
        val buildFileContent = """
            plugins {
               id("com.google.devtools.ksp")
               kotlin("jvm")
            }
            
            dependencies {
                implementation("com.code.factory:ksp-processor:0.0.1")
                implementation("ksp", "com.code.factory:code-factory-ksp:0.0.1")
            }
        """
        writeFile(buildFileContent, buildFile)
    }

    @Test
    fun `run plugin task`() {
        val result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments("build")
            .build()

        assertEquals(result.task(":build")?.outcome, TaskOutcome.SUCCESS)
    }
}