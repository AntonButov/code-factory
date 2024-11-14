import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

class PluginTest {

    @Test
    fun `plugin test`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.gradle.plugin")
        project.tasks.forEach { println(it.name) }
        assertNotNull(project.tasks.findByName("codeFactoryTask"))
    }
}