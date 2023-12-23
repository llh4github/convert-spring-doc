package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.MarkerAnnotationExpr
import com.github.javaparser.ast.expr.MemberValuePair
import com.github.javaparser.ast.expr.Name
import com.github.javaparser.ast.expr.NormalAnnotationExpr
import io.swagger.annotations.ApiImplicitParam
import io.swagger.v3.oas.annotations.Parameter
import org.apache.logging.log4j.kotlin.Logging

/**
 *
 * Created At 2023/12/21 22:42
 * @author llh
 */
open class ApiImplicitParamToParameter(private val typeDeclaration: TypeDeclaration<*>) : Logging {
    private val className: String by lazy { typeDeclaration.name.asString() }
    private val sourceAnnoName: String = ApiImplicitParam::class.simpleName!!
    private val targetAnnoName: String = Parameter::class.simpleName!!

    //    @ApiResponse(message = "", code = 22)
    open fun convert() {
        typeDeclaration.methods.forEach { method ->
            method.annotations.filter { it.name.asString() == sourceAnnoName }
                .forEach {
                    when (it) {
                        is MarkerAnnotationExpr -> markerAnnotation(it, method)
                        is NormalAnnotationExpr -> normalAnnotation(it, method)
                        else -> logger.debug("$className ${method.name.asString()} 方法  $sourceAnnoName 注解类型不正解")
                    }
                }
        }
    }

    private fun markerAnnotation(anno: MarkerAnnotationExpr, method: MethodDeclaration) {
        val needAddAnno = MarkerAnnotationExpr(targetAnnoName)
        method.addAnnotation(needAddAnno)
        typeDeclaration.tryAddImportToParentCompilationUnit(Parameter::class.java)
        anno.remove()
    }

    private fun normalAnnotation(anno: NormalAnnotationExpr, method: MethodDeclaration) {
        val pairs = handleProperties(anno.pairs)
        val tagsAnno = NormalAnnotationExpr(Name(targetAnnoName), pairs)
        method.addAnnotation(tagsAnno)
        anno.remove()
    }

    protected fun handleProperties(pairs: NodeList<MemberValuePair>): NodeList<MemberValuePair> {
        val rs = NodeList<MemberValuePair>()
        pairs.forEach {
            when (val name = it.name.asString()) {
                "name" -> rs.add(MemberValuePair("name", it.value))
                "value" -> rs.add(MemberValuePair("description", it.value))
                "defaultValue" -> rs.add(MemberValuePair("example", it.value))
                "required" -> rs.add(MemberValuePair("required", it.value))
                else -> logger.debug("$sourceAnnoName 注解的 $name 在 $targetAnnoName 注解中无对应属性")
            }
        }
        return rs
    }
}