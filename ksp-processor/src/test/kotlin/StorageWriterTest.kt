import com.code.factory.writer.StorageWriter
import com.code.factory.writer.storageWriter
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class StorageWriterTest: StringSpec ({

    lateinit var storageWriter: StorageWriter

    beforeTest {
        storageWriter = storageWriter()
    }

    afterTest {
        storageWriter.clean()
    }

    "writer and read immediately" {
        storageWriter.setKotlinPath("kotlinPath")

        val readPath = storageWriter.getKotlinPath()
        readPath shouldBe "kotlinPath"
    }

    "writer and read new instance" {
        storageWriter.setKotlinPath("KotlinPath")

        val readPathNewInstance = storageWriter().getKotlinPath()

        readPathNewInstance shouldBe "KotlinPath"
    }

})