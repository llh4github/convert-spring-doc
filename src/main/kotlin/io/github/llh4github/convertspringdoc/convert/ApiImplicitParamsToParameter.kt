package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.*
import io.swagger.annotations.ApiImplicitParams
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.Parameters
import org.apache.logging.log4j.kotlin.Logging

/**
 *
 * Created At 2023/12/21 22:42
 * @author llh
 */
class ApiImplicitParamsToParameter(
    private val typeDeclaration: TypeDeclaration<*>
) : ApiImplicitParamToParameter(typeDeclaration), Logging {
    private val className: String by lazy { typeDeclaration.name.asString() }
    private val sourceAnnoName: String = ApiImplicitParams::class.simpleName!!
    private val targetAnnoName: String = Parameters::class.simpleName!!
    override fun convert() {
        typeDeclaration.methods.forEach { method ->
            method.annotations.filter { it.name.asString() == sourceAnnoName }
                .forEach {
                    when (it) {
                        is SingleMemberAnnotationExpr -> singleMemberAnnotation(it, method)
                        is NormalAnnotationExpr -> normalAnnotation(it, method)
                        else -> logger.debug("$className ${method.name.asString()} 方法  $sourceAnnoName 注解类型不正解")
                    }
                }
        }
    }

    private fun singleMemberAnnotation(anno: SingleMemberAnnotationExpr, method: MethodDeclaration) {
        val needAddAnno = MarkerAnnotationExpr(targetAnnoName)
        method.addAnnotation(needAddAnno)
        typeDeclaration.tryAddImportToParentCompilationUnit(Parameters::class.java)
        anno.remove()
    }

     fun normalAnnotation3(anno: NormalAnnotationExpr, method: MethodDeclaration) {
        val pairs = NodeList<MemberValuePair>()
        anno.pairs.forEach {
            when (val name = it.name.asString()) {
                "name" -> pairs.add(MemberValuePair("name", StringLiteralExpr(it.value.toString())))
                "value" -> pairs.add(MemberValuePair("description", it.value))
                "defaultValue" -> pairs.add(MemberValuePair("example", it.value))
                "required" -> pairs.add(MemberValuePair("required", it.value))
                else -> logger.debug("$sourceAnnoName 注解的 $name 在 $targetAnnoName 注解中无对应属性")
            }
        }
        val tagsAnno = NormalAnnotationExpr(Name(targetAnnoName), pairs)
        method.addAnnotation(tagsAnno)
        anno.remove()
    }
}