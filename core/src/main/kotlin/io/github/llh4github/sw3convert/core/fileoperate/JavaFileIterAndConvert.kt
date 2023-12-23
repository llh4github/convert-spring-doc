package io.github.llh4github.sw3convert.core.fileoperate

import io.github.llh4github.sw3convert.core.convert.SwAnnoConvertFactory
import org.apache.logging.log4j.kotlin.Logging
import java.io.File

/**
 *
 * Created At 2023/12/23 17:17
 * @author llh
 */
object JavaFileIterAndConvert : Logging {
    fun convertJavaFile(path: String) {
        val filePath = File(path)
        if (!filePath.exists()) {
            logger.error("$path 指定文件不存在！")
        }
        val files = javaFileList(filePath)
        files.map { it.name }.forEach{ println(it) }
    }
}