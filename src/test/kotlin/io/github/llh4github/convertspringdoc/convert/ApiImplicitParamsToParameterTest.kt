package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.printer.DefaultPrettyPrinter
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 *
 *
 * Created At 2023/12/22 17:40
 * @author llh
 */
class ApiImplicitParamsToParameterTest{
    @Test
    fun `test @ApiImplicitParams()`(){
        val clazz = """
           import io.swagger.annotations.ApiImplicitParams;
           import io.swagger.annotations.ApiImplicitParam;
           public class Demo{
            @ApiImplicitParams({@ApiImplicitParam(name="a")})
            public void test(){}
           } 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach {ApiImplicitParamsToParameter(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }

    @Test
    fun `test @ApiImplicitParams(value=xx)`(){
        val clazz = """
           import io.swagger.annotations.ApiImplicitParams;
           import io.swagger.annotations.ApiImplicitParam;
           public class Demo{
            @ApiImplicitParams(value={@ApiImplicitParam(name="a"),@ApiImplicitParam()})
            public void test(){}
           } 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach {ApiImplicitParamsToParameter(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }
}