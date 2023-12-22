package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.printer.DefaultPrettyPrinter
import io.swagger.annotations.ApiModel
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 *
 *
 * Created At 2023/12/22 14:44
 * @author llh
 */
class ApiModelToSchemaTest{

    @Test
    fun `test @ApiModel()`(){
        val clazz = """
           import io.swagger.annotations.ApiModel;
           @ApiModel()
           public class Demo{} 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach {ApiModelToSchema(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }
    @Test
    fun `test @ApiModel(value=xx,description=xx)`(){
        val clazz = """
           import io.swagger.annotations.ApiModel;
           @ApiModel(value="a",description="bb")
           public class Demo{} 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach {ApiModelToSchema(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }
}