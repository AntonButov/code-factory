import com.code.factory.writer.WriterData
import io.ktor.client.plugins.cache.storage.FileStorage
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.kotlin.konan.file.File
import java.nio.file.Files
import kotlin.io.path.Path

interface Storage {
    fun getAllDeclaration(): String?
    fun addDeclarations(declarationsCode: String)
    fun getInterfaceWithOutImplementation(): WriterData?
    fun setInterfaceWithOutImplementation(writerData: WriterData)
    fun clean()
}

fun storage(): Storage = StorageImp()

private val allDeclarationsDir = File("tmp/code-factory")
private val allDeclarationsFile = File(allDeclarationsDir, "AllDeclarations.txt")

private val interfaceWithOutDeclarationDir = File("tmp/code-factory")
private val interfaceWithOutDeclarationFile = File(interfaceWithOutDeclarationDir, "InterfaceWithOutImplementation.txt")


internal class StorageImp() : Storage {

    private var allDeclarations: String? = readAllDeclarations()
    private var interfaceWithOutImplementation: WriterData? = readInterfaceWithOutImplementation()

    override fun getInterfaceWithOutImplementation(): WriterData? {
        return interfaceWithOutImplementation
    }

    override fun getAllDeclaration(): String? {
        return allDeclarations
    }

    override fun addDeclarations(declarationsCode: String) {
        allDeclarations = allDeclarations?.let {
            "$allDeclarations \n$declarationsCode"
        } ?: declarationsCode
        saveAllDeclarations(declarationsCode)
    }

    private fun saveAllDeclarations(allDeclarations: String) {
        if (!allDeclarationsDir.exists) {
            allDeclarationsDir.mkdirs()
        }
        allDeclarationsFile.writeText(allDeclarations)
    }

    private fun saveInterfaceWithOutImplementation(writerData: WriterData) {
        if (!interfaceWithOutDeclarationDir.exists) {
            interfaceWithOutDeclarationDir.mkdirs()
        }
        interfaceWithOutDeclarationFile.writeText(Json.encodeToString(writerData))
    }

    private fun readInterfaceWithOutImplementation(): WriterData? =
        runCatching {
            Json.decodeFromString<WriterData>(Files.readString(Path(interfaceWithOutDeclarationFile.path)))
        }.getOrNull()

    private fun readAllDeclarations(): String? =
        runCatching {
            Files.readString(Path(allDeclarationsFile.path))
        }.getOrNull()

    override fun setInterfaceWithOutImplementation(writerData: WriterData) {
        interfaceWithOutImplementation = writerData
        saveInterfaceWithOutImplementation(writerData)
    }

    override fun clean() {
        allDeclarations = null
        interfaceWithOutImplementation = null
        runCatching {
            allDeclarationsFile.delete()
        }
        runCatching {
            interfaceWithOutDeclarationFile.delete()
        }
    }
}