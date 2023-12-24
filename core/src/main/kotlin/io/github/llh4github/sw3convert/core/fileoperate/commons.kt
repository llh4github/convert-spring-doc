package io.github.llh4github.sw3convert.core.fileoperate

import java.io.File

/**
 * Java项目源码存放路径
 */
private val srcDir = "src" + File.separator + "main" + File.separator + "java"

private val buildDir = listOf("build", "target")

internal fun javaFileList(dir: File): List<File> {
    return dir.walk()
        .filter { isJavaFile(it) }
        .filter { it.isFile }
        .toList()
}

fun replacePath(file: File, targetDir: File): File {
    val sourceFilePath = file.absolutePath
    val prefix = File(sourceFilePath.split(srcDir)[0]).parentFile.absolutePath
    val fileRelativePath = sourceFilePath.removePrefix(prefix)
    val fileRelativePathString = if (fileRelativePath.startsWith(File.separator)) {
        fileRelativePath
    } else {
        File.separator + fileRelativePath
    }
    val targetPath = targetDir.absolutePath + fileRelativePathString
    return File(targetPath)
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
    return file.absolutePath.contains(srcDir)
            && file.extension == "java"
}
