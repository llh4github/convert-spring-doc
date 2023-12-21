package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.printer.DefaultPrettyPrinter
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 *
 * Created At 2023/12/21 21:43
 * @author llh
 */
class ApiOperationToOperationTest{

    @Test
    fun a(){
       val clazz = """
           import io.swagger.annotations.ApiOperation;
           class Demo{
                @ApiOperation("test")
                public void get(){}
           }
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach {ApiOperationToOperation(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }

    @Test
    fun a2(){
        val clazz = """
           import io.swagger.annotations.ApiOperation;
           class Demo{
                @ApiOperation(value = "test",notes="test notes",tags={"a","b","c"})
                public void get(){}
           }
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach {ApiOperationToOperation(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }
}