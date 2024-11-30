import com.code.factory.File
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class FileTest {

    @Test
    fun `when save to file file should exist`() {
        val tempFile = "file.kt"
        val myContent = "someContent"
        File.writeToTempFile(tempFile, myContent)
        assertEquals(myContent, File.readFromFile(tempFile))
        File.removeTempFile(tempFile)
    }

    @Test
    fun `when file deleted it not exist`() {
        val tempFile = "someFile.kt"
        File.removeTempFile(tempFile)
        assertFalse { File.isTempFileExist(tempFile) }
    }

}