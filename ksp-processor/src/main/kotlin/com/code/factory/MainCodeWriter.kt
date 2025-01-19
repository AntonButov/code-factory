package com.code.factory

import com.code.factory.writer.StorageWriter
import com.code.factory.writer.WriterData
import com.code.factory.writer.appendText
import java.io.File
import java.io.File.separator
import java.io.FileOutputStream

interface MainCodeWriter {
    fun write(writerData: WriterData)
}

fun mainCodeWriter(storageWriter: StorageWriter): MainCodeWriter = MainCodeWriterImpl(storageWriter)

class MainCodeWriterImpl(
    private val storageWriter: StorageWriter,
): MainCodeWriter {
    override fun write(writerData: WriterData) {
        val baseFile = File(storageWriter.getKotlinPath())
        val file = File(baseFile, pathOf(writerData.packageName, writerData.name))

        if (file.parentFile.exists().not()) {
            file.parentFile.mkdirs()
        }

        FileOutputStream(file).use {
            it.appendText(writerData.code)
        }
    }

    private fun pathOf(packageName: String, fileName: String, extensionName: String = "kt"): String {
        val packageDirs = if (packageName != "") "${packageName.split(".").joinToString(separator)}$separator" else ""
        val extension = if (extensionName != "") ".$extensionName" else ""
        return "$packageDirs$fileName$extension"
    }
}