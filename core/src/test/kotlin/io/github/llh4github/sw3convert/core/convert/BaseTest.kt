package io.github.llh4github.sw3convert.core.convert

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.printer.DefaultPrettyPrinter
import kotlin.test.assertEquals

/**
 *
 * Created At 2023/12/23 16:23
 * @author llh
 */
abstract class BaseTest {
    /**
    testcase.put("""

    """.trimIndent(),"""

    """.trimIndent())
     */
    protected val testcase: MutableMap<String, String> = mutableMapOf()

    protected fun runConvertTest(convertFun: (TypeDeclaration<*>) -> Unit) {
        testcase.forEach { (k, v) ->
            val parserRs = StaticJavaParser.parse(k)
            parserRs.types.forEach { convertFun(it) }
            val rs = DefaultPrettyPrinter().print(parserRs)
            assertEquals(v.trimIndent(), rs.trimIndent())
        }
    }
}