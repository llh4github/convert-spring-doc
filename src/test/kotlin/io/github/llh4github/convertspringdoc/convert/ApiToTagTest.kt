package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.printer.DefaultPrettyPrinter
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

/**
 *
 *
 * Created At 2023/12/20 18:25
 * @author llh
 */
class ApiToTagTest {

    val clazz = """
           import io.swagger.oas.annotations.Api;
           @Api("demo")
           public class Demo{} 
       """.trimIndent()


    @Test
    fun t12() {
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach { ApiToTag(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }

    @Test
    fun `test @Api(value=xx)`(){
        val clazz = """
           import io.swagger.oas.annotations.Api;
           @Api(value = "demo",hidden=false)
           public class Demo{} 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach { ApiToTag(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }

    @Test
    fun `test @Api(tags={xx})`(){
        val clazz = """
           import io.swagger.oas.annotations.Api;
           @Api(tags= {"a","b","c"})
           public class Demo{} 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach { ApiToTag(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }

    @Test
    fun `test @Api(tags=aa)`(){
        val clazz = """
           import io.swagger.oas.annotations.Api;
           @Api(tags= "abc")
           public class Demo{} 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach { ApiToTag(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }

    @Test
    fun `test @Api(tags=aa,value=bb)`(){
        val clazz = """
           import io.swagger.oas.annotations.Api;
           @Api(tags= "abc",value="bb")
           public class Demo{} 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach { ApiToTag(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }

    @Test
    fun `test @Api(tags={},value=xx)`(){
        val clazz = """
           import io.swagger.oas.annotations.Api;
           @Api(tags= {"a","b","c"},value="bb")
           public class Demo{} 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach { ApiToTag(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }
}