package io.github.llh4github.convertspringdoc.sw2

import com.github.javaparser.StaticJavaParser
import com.github.javaparser.printer.DefaultPrettyPrinter
import io.swagger.v3.oas.annotations.tags.Tag
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

/**
 *
 *
 * Created At 2023/12/15 18:52
 * @author llh
 */

class ApiAnnoTest {
    private val logger = LoggerFactory.getLogger(ApiAnnoTest::class.java)
    @Test
    fun t2(){
       logger.info("hello log")
    }
    @Test
    fun t() {
        val clazz = """
           import io.swagger.oas.annotations.Api;
           @Api("demo")
           public class Demo{} 
       """.trimIndent()
        val parserRs = StaticJavaParser.parse(clazz)

        val im = parserRs.imports
        parserRs.types.forEach {

//            it.addSingleMemberAnnotation(Tag::class.java, """"demo"""")
            val addAnno = it.addAndGetAnnotation(Tag::class.java)
            addAnno.addPair("tag","123")
            it.tryAddImportToParentCompilationUnit(Tag::class.java)
            it.annotations.forEach {
                it.remove()
            }
        }
        println(DefaultPrettyPrinter().print(parserRs))


    }
}