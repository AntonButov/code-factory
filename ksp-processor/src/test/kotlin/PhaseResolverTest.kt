import com.code.factory.ksp.PhaseResolver
import com.code.factory.ksp.PhaseResolverImpl
import com.code.factory.ksp.Phases
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PhaseResolverTest : StringSpec({

    lateinit var phaseResolver: PhaseResolver

    beforeTest {
        phaseResolver = PhaseResolverImpl()
    }

    "phase main" {
        val paths =
            listOf("/Users/antonbutov/StudioProjects/code-factory/integration-test/src/main/kotlin/ForGenerate.kt")
        phaseResolver.resolvePhase(paths) shouldBe Phases.Main
    }

    "phase tests" {
        val paths = listOf(
            "/Users/antonbutov/StudioProjects/code-factory/integration-test/src/main/kotlin/ForGenerate.kt",
            "/Users/antonbutov/StudioProjects/code-factory/integration-test/src/test/kotlin/ForGenerateTest.kt"
        )
        phaseResolver.resolvePhase(paths) shouldBe Phases.Tests
    }

    "phase generated" {
        val paths = listOf(
            "/Users/antonbutov/StudioProjects/code-factory/integration-test/src/main/kotlin/ForGenerate.kt",
            "/Users/antonbutov/StudioProjects/code-factory/integration-test/generated/ksp/src/main/kotlin/ForGenerateKsp.kt"
        )
        phaseResolver.resolvePhase(paths) shouldBe Phases.Generated
    }

})