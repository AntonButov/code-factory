import com.gradle.plugin.FileCompileChecker
import org.gradle.internal.impldep.org.junit.Before
import org.gradle.internal.impldep.org.junit.Test

class CheckCompilerTest {

    private lateinit var checker: FileCompileChecker

    @Before
    fun setUp() {
        checker = FileCompileChecker()
    }

    @Test
    fun `Hello word should compile`() {

    }

}