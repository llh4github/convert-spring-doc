package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.Name
import com.github.javaparser.ast.expr.SimpleName
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr
import io.swagger.v3.oas.annotations.tags.Tag
import org.apache.logging.log4j.kotlin.Logging

/**
 *
 *
 * Created At 2023/12/20 15:55
 * @author llh
 */
class ApiToTag(private val typeDeclaration: TypeDeclaration<*>) : Logging {

    private val className: String by lazy { typeDeclaration.name.toString() }
    fun convert() {
        val noAnno = typeDeclaration.annotations.count { it.name.toString() == "Api" } == 0
        if (noAnno) {
            logger.debug("$className 类没有目标注解: Api")
            return
        }
        typeDeclaration.annotations.filter { it.name.toString() == "Api" }
            .forEach {
                if (it is SingleMemberAnnotationExpr) singleMember(it)
            }


    }

    private fun singleMember(anno: SingleMemberAnnotationExpr) {
        val value = anno.memberValue
        typeDeclaration.tryAddImportToParentCompilationUnit(Tag::class.java)
        val needAdd = SingleMemberAnnotationExpr(Name("Tag"), value)
        typeDeclaration.addAnnotation(needAdd)
        anno.remove()
        logger.debug("$className 注解 $anno 替换为 $needAdd")
    }


}