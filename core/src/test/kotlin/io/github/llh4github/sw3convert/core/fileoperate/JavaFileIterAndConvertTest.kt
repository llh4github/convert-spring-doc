package io.github.llh4github.sw3convert.core.fileoperate

import io.github.llh4github.sw3convert.core.dto.ConvertParams
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.io.File

/**
 *
 * Created At 2023/12/23 18:58
 * @author llh
 */
class JavaFileIterAndConvertTest {

    /**
     * test only local
     */
    @Test
//    @Disabled
    fun test() {
        val sourcePath = "C:\\code-projects\\jvm\\testcase"
        val params = ConvertParams(sourcePath)
        JavaFileIterAndConvert.convertJavaFile(params)
    }

    /**
     * test only local
     */
    @ParameterizedTest
    @CsvSource(
        value = [
            "C:\\code-projects\\jvm\\testcase\\src\\main\\java\\io\\github\\llh4github\\sw3convert\\testcase\\TestcaseApplication.java",
            "C:\\TestcaseApplication.java",
        ]
    )
    @Disabled
    fun replace_path(path: String) {
        val file = replacePath(File(path), File("D:\\test"))
        println(file.absoluteFile)
    }
}