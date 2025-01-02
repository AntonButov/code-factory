import com.code.factory.writer.WriterData
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain

class StorageTest: StringSpec ({

    lateinit var storage: Storage

    beforeTest {
        storage = StorageImp()
    }

    afterTest {
        StorageImp().clean()
    }

    "read default all declarations" {
        val defaultAllDeclarations = storage.getAllDeclaration()

        defaultAllDeclarations shouldBe null
    }

    "read allDeclaration test immediately" {
        val allDeclarations = "Some code."
        storage.addDeclarations(allDeclarations)

        val allDeclarationsRead = storage.getAllDeclaration()
        allDeclarationsRead shouldBe allDeclarations
    }

    "read test other phase" {
        storage.clean()
        val allDeclarations = "Some code other phase."
        storage.addDeclarations(allDeclarations)

        val newStorage = StorageImp()
        val allDeclarationsRead = newStorage.getAllDeclaration()
        allDeclarationsRead shouldContain allDeclarations
    }

    "when add first then add second should contaminate" {
        val firstDeclaration = "Some code first."
        storage.addDeclarations(firstDeclaration)

        val secondDeclaration = "Some code second."
        storage.addDeclarations(secondDeclaration)

        val allDeclarationsRead = storage.getAllDeclaration()
        allDeclarationsRead shouldContain firstDeclaration
        allDeclarationsRead shouldContain secondDeclaration
    }

    "when clean should return null" {
        val allDeclarationsRead = storage.getAllDeclaration()
        allDeclarationsRead shouldBe null
        val interfaceWithOutImplementation = storage.getInterfaceWithOutImplementation()
        interfaceWithOutImplementation shouldBe null
    }

    "read default interfaceWithOutImplementation should be null" {
        val interfaceWithOutImplementation = storage.getInterfaceWithOutImplementation()

        interfaceWithOutImplementation shouldBe null
    }

    val interfaceWithOutImplementation = WriterData(
        code = "Some code interfaceWithOutImplementation.",
        packageName = "packageName",
        name = "name"
    )

    "save then read interfaceWithOutImplementation should return some immediate" {
        storage.setInterfaceWithOutImplementation(interfaceWithOutImplementation)
        val interfaceWithOutImplementationRead = storage.getInterfaceWithOutImplementation()
        interfaceWithOutImplementationRead shouldBe interfaceWithOutImplementation
    }

    "save then read interfaceWithOutImplementation should return some after restart" {
        storage.setInterfaceWithOutImplementation(interfaceWithOutImplementation)

        val interfaceWithOutImplementationRead = StorageImp().getInterfaceWithOutImplementation()
        interfaceWithOutImplementationRead shouldBe interfaceWithOutImplementation
    }
})