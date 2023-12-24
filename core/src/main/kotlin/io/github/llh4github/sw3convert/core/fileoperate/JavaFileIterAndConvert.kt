package io.github.llh4github.sw3convert.core.fileoperate

import io.github.llh4github.sw3convert.core.convert.SwAnnoConvertFactory
import io.github.llh4github.sw3convert.core.dto.ConvertParams
import io.github.llh4github.sw3convert.core.dto.ParseResult
import org.apache.logging.log4j.kotlin.Logging
import java.io.File

/**
 *
 * Created At 2023/12/23 17:17
 * @author llh
 */
object JavaFileIterAndConvert : Logging {
    fun convertJavaFile(params: ConvertParams) {
        val path = params.sourcePath
        val filePath = File(path)
        if (!filePath.exists()) {
            logger.error("$path 指定文件不存在！")
        }
        val files = javaFileList(filePath)
        files.mapNotNull { modifiedFile(it) }
            .toList()
    }
    private fun modifiedFile(file:File): Pair<File, ParseResult>? {
       val parseResult = SwAnnoConvertFactory.convert(file)
        if (parseResult.modified) {
            return Pair(file,parseResult)
        }
        return null
    }
}