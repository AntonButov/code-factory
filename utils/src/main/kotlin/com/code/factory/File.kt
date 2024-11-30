package com.code.factory

import java.io.File

object File {

    private const val TEMP_DIR = "build/tmp/code-factory"

    fun writeToTempFile(fileName: String, content: String) {
        createTempFile(fileName).writeText(content)
    }

    @Throws(NullPointerException::class)
    fun readFromFile(fileName: String): String? {
        val file = File(TEMP_DIR, fileName)
        return file.readText()
    }

    private fun createTempFile(fileName: String): File {
        val tempDirFile = File(TEMP_DIR)
        if (!tempDirFile.exists()) {
            tempDirFile.mkdirs()
        }
        return File(TEMP_DIR, fileName)
    }

    fun removeTempFile(file: String): Boolean = File(TEMP_DIR, file).delete()

    fun isTempFileExist(file: String): Boolean = File(TEMP_DIR, file).exists()
}