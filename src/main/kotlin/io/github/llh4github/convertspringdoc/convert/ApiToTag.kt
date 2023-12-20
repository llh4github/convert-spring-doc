package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.*
import io.github.llh4github.convertspringdoc.dto.sw2.ApiAnno
import io.github.llh4github.convertspringdoc.dto.sw3.TagAnno
import io.swagger.v3.oas.annotations.Hidden
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
        val noAnno = typeDeclaration.annotations.count { it.name.toString() == ApiAnno().annoName } == 0
        if (noAnno) {
            logger.debug("$className 类没有目标注解: Api")
            return
        }
        typeDeclaration.annotations.filter { it.name.toString() == "Api" }
            .forEach {
                when (it) {
                    is SingleMemberAnnotationExpr -> singleMember(it)
                    is NormalAnnotationExpr -> normalAnnotation(it)
                    else -> logger.debug("$className 无Api注解")
                }
            }


    }

    private fun singleMember(anno: SingleMemberAnnotationExpr) {
        val value = anno.memberValue
        typeDeclaration.tryAddImportToParentCompilationUnit(Tag::class.java)
        val needAdd = SingleMemberAnnotationExpr(Name(TagAnno().annoName), value)
        typeDeclaration.addAnnotation(needAdd)
        anno.remove()
        logger.debug("$className 注解 $anno 替换为 $needAdd")
    }

    private fun normalAnnotation(anno: NormalAnnotationExpr) {
        anno.pairs.forEach {
           handleHiddenProperty(it)
        }
        anno.remove()
    }
    private fun handleValueProperty(pair: MemberValuePair) {
        if (pair.name.toString() != "value") {
            logger.debug("$className Api注解无 hidden属性")
            return
        }
        typeDeclaration.tryAddImportToParentCompilationUnit(Hidden::class.java)
        val needAdd = MarkerAnnotationExpr(Name("Hidden"))
        typeDeclaration.addAnnotation(needAdd)
    }
    private fun handleHiddenProperty(pair: MemberValuePair) {
        if (pair.name.toString() != "hidden") {
            logger.debug("$className Api注解无 hidden属性")
            return
        }
        typeDeclaration.tryAddImportToParentCompilationUnit(Hidden::class.java)
        val needAdd = MarkerAnnotationExpr(Name("Hidden"))
        typeDeclaration.addAnnotation(needAdd)
    }


}