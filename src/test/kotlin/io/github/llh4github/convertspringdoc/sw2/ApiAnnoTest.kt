package io.github.llh4github.convertspringdoc.sw2

import com.github.javaparser.StaticJavaParser
import org.junit.jupiter.api.Test

/**
 *
 *
 * Created At 2023/12/15 18:52
 * @author llh
 */

class ApiAnnoTest {

    @Test
    fun t(): Unit {
        val clazz = """
           @Api("demo")
           public class Demo{}
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)

        parserRs.types.forEach {
            it.annotations
        }
    }
}