package com.code.factory.writer

import com.code.factory.MainCodeWriter
import com.google.devtools.ksp.processing.CodeGenerator


interface Writer {
    fun setKotlinPath(packageName: String, name: String)
    fun write(writerData: WriterData)
}

fun writer(storageWriter: StorageWriter, mainCodeWriter: MainCodeWriter, codeGenerator: CodeGenerator): Writer = WriterImpl(storageWriter, mainCodeWriter, codeGenerator)