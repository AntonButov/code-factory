package com.code.factory.writer

import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files
import kotlin.io.path.Path

interface StorageWriter {

    fun setKotlinPath(path: String)
    fun getKotlinPath(): String
    fun clean()
}

fun storageWriter(): StorageWriter = StorageWriterImpl()


private val kotlinPathDir = File("tmp/kotlinPath")
private val kotlinPathFile = File(kotlinPathDir, "KotlinPath.txt")

    class StorageWriterImpl: StorageWriter {
        override fun setKotlinPath(path: String) {
            if (kotlinPathDir.exists().not()) {
                kotlinPathDir.mkdirs()
            }

            FileOutputStream(kotlinPathFile).use {
                it.appendText(path)
            }
        }

        override fun getKotlinPath(): String {
            return Files.readString(Path(kotlinPathFile.path))
        }

        override fun clean() {
            kotlinPathDir.delete()
        }

    }
