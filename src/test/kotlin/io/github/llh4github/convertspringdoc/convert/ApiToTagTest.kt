package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.printer.DefaultPrettyPrinter
import org.apache.logging.log4j.kotlin.logger
import org.junit.jupiter.api.Test

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
    fun t1() {
        val parserRs = StaticJavaParser.parse(clazz)
        parserRs.types.forEach { ApiToTag(it).convert() }
        val rs = DefaultPrettyPrinter().print(parserRs)
        logger.info(rs)
    }
}