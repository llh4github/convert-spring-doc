package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.MemberValuePair
import com.github.javaparser.ast.expr.Name
import com.github.javaparser.ast.expr.NormalAnnotationExpr
import com.github.javaparser.ast.expr.StringLiteralExpr
import io.swagger.annotations.ApiResponse
import org.apache.logging.log4j.kotlin.Logging

/**
 *
 * Created At 2023/12/21 22:42
 * @author llh
 */
class ApiResponseConvert(private val typeDeclaration: TypeDeclaration<*>) : Logging {
    private val className: String by lazy { typeDeclaration.name.asString() }
    private val sourceAnnoName: String = ApiResponse::class.simpleName!!
    private val targetAnnoName: String = sourceAnnoName

//    @ApiResponse(message = "", code = 22)
    fun convert() {
        val list = typeDeclaration.methods
        list.forEach { method ->
            method.annotations.filter { it.name.asString() == sourceAnnoName }
                .forEach {
                   when(it){
                       is NormalAnnotationExpr -> normalAnnotation(it, method)
                       else -> logger.debug("$className  $sourceAnnoName 注解类型不正解")
                   }
                }
        }
    }

    private fun normalAnnotation(anno: NormalAnnotationExpr, method: MethodDeclaration) {
        val list = anno.pairs
        val pairs = NodeList<MemberValuePair>()
        list.forEach {
            when (val name = it.name.asString()) {
                "code" -> pairs.add(MemberValuePair("responseCode", StringLiteralExpr(it.value.toString())))
                "message" -> pairs.add(MemberValuePair("description", it.value))
                else -> logger.debug("$sourceAnnoName 注解的 $name 在 $targetAnnoName 注解中无对应属性")
            }
        }
        val tagsAnno = NormalAnnotationExpr(Name(targetAnnoName), pairs)
        method.addAnnotation(tagsAnno)
        anno.remove()
    }
}