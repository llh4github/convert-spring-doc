package io.github.llh4github.sw3convert.core.fileoperate

import java.io.File

private val buildDir = listOf("build", "target")
internal fun javaFileList(dir: File): List<File> {
    return dir.walk()
        .filter { isJavaFile(it) }
        .filter { it.isFile }
        .toList()
}

private fun isJavaFile(file: File): Boolean {
    if (file.isDirectory)
        return noBuildDir(file)
    return noJavaFile(file)
}

private fun noBuildDir(dir: File): Boolean {
    if (dir.isDirectory) {
        return !buildDir.any { it == dir.name }
    }
    return true
}

private fun noJavaFile(file: File): Boolean {
    return file.extension == "java"
}
