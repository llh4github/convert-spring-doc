package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.FieldDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.MarkerAnnotationExpr
import com.github.javaparser.ast.expr.MemberValuePair
import com.github.javaparser.ast.expr.Name
import com.github.javaparser.ast.expr.NormalAnnotationExpr
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.v3.oas.annotations.media.Schema
import org.apache.logging.log4j.kotlin.Logging

/**
 *
 *
 * Created At 2023/12/22 14:26
 * @author llh
 */
class ApiModelPropertyToSchema(private val typeDeclaration: TypeDeclaration<*>) : Logging {

    private val className: String by lazy { typeDeclaration.name.asString() }
    private val sourceAnnoName: String = ApiModelProperty::class.simpleName!!
    private val targetAnnoName: String = Schema::class.simpleName!!

    fun convert() {
        typeDeclaration.fields.forEach { fieldsAnnoHandle(it) }
    }

    private fun fieldsAnnoHandle(field: FieldDeclaration) {
        field.annotations.forEach {
            when (it) {
                is MarkerAnnotationExpr -> handleMarkerAnno(it)
                is NormalAnnotationExpr -> handleNormalAnno(it)
                else -> logger.debug("$className ${it.name.asString()} 字段 $sourceAnnoName 注解类型不正确")
            }
        }
    }

    private fun handleMarkerAnno(anno: MarkerAnnotationExpr) {
        val needAdd = MarkerAnnotationExpr(targetAnnoName)
        typeDeclaration.tryAddImportToParentCompilationUnit(Schema::class.java)
        typeDeclaration.addAnnotation(needAdd)
        anno.remove()
    }

    private fun handleNormalAnno(anno: NormalAnnotationExpr) {
        val pairs = NodeList<MemberValuePair>()
        anno.pairs.forEach {
            when (val key = it.name.asString()) {
                "name" -> pairs.add(MemberValuePair("name", it.value))
                "value" -> pairs.add(MemberValuePair("title", it.value))
                "notes" -> pairs.add(MemberValuePair("description", it.value))
                "hidden" -> pairs.add(MemberValuePair("hidden", it.value))
                else -> logger.debug("$className  $sourceAnnoName 注解 $key 属性不支持转换到 $targetAnnoName 注解")
            }
        }
        typeDeclaration.tryAddImportToParentCompilationUnit(Schema::class.java)
        val needAddAnno = NormalAnnotationExpr(Name(targetAnnoName), pairs)
        typeDeclaration.addAnnotation(needAddAnno)
        anno.remove()
    }
}