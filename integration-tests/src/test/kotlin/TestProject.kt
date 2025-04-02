import org.gradle.testkit.runner.GradleRunner
import java.io.File
import kotlin.io.path.createTempDirectory

fun createTestProject(name: String = "test-project"): TestProject = TestProject(createTempDirectory(prefix = name).toFile())
class TestProject(private val root: File) {
    init {
        check(root.exists() || root.mkdirs()) { "Could not create root directory: $root" }
    }

    fun withSettings(content: String = ""): TestProject {
        write("settings.gradle.kts", content)
        return this
    }

    fun withLocalProperties(content: String): TestProject {
        write("local.properties", content)
        return this
    }

    fun withBuildScript(content: String): TestProject {
        write("build.gradle.kts", content)
        return this
    }

    fun withKotlinSource(
        path: String,
        content: String,
    ): TestProject {
        val file = root.resolve("src/main/kotlin/$path")
        file.parentFile.mkdirs()
        file.writeText(content)
        return this
    }

    fun withKotlinSource(
        path: String,
        content: File,
    ): TestProject {
        val contentFile = File(System.getProperty("user.dir"), content.path)
        val contentCode = contentFile.readText()
        val file = root.resolve("src/main/kotlin/$path")
        file.parentFile.mkdirs()
        file.writeText(contentCode)
        return this
    }

    fun withFile(
        path: String,
        content: String,
    ): TestProject {
        val file = root.resolve(path)
        file.parentFile.mkdirs()
        file.writeText(content)
        return this
    }

    fun build(vararg args: String) =
        GradleRunner.create()
            .withProjectDir(root)
            .withArguments(*args)
            .withDebug(true)
            .forwardOutput()
            .build()

    private fun write(
        path: String,
        content: String,
    ) {
        val file = root.resolve(path)
        file.parentFile.mkdirs()
        file.writeText(content)
    }

    fun resolve(path: String): File = root.resolve(path)

    fun getAllPaths(): List<String> = root.walk().map { it.path }.toList()

    fun cleanup() = root.deleteRecursively()
}
