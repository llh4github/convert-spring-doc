package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.printer.DefaultPrettyPrinter
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 *
 * Created At 2023/12/21 22:53
 * @author llh
 */
class ApiResponseConvertTest{
    @Test
    fun t(){
        val clazz = """
           import io.swagger.annotations.ApiResponse;
           class Demo{
                @ApiResponse(message="test",code=111)
                public void get(){}
           }
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach {ApiResponseConvert(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }
}