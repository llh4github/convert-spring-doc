package io.github.llh4github.sw3convert.core.convert

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.*
import io.swagger.annotations.ApiModel
import io.swagger.v3.oas.annotations.media.Schema
import org.apache.logging.log4j.kotlin.Logging

/**
 *
 *
 * Created At 2023/12/22 14:26
 * @author llh
 */
class ApiModelToSchema(
    private val typeDeclaration: TypeDeclaration<*>
) : SwAnnoConvert, Logging {

    private val className: String by lazy { typeDeclaration.name.asString() }
    private val sourceAnnoName: String = ApiModel::class.simpleName!!
    private val targetAnnoName: String = Schema::class.simpleName!!

    override fun convert() {
        typeDeclaration.annotations.filter { it.name.asString() == sourceAnnoName }
            .forEach {
                when (it) {
                    is MarkerAnnotationExpr -> handleMarkerAnno(it)
                    is SingleMemberAnnotationExpr -> handleSingleMemberAnno(it)
                    is NormalAnnotationExpr -> handleNormalAnno(it)
                    else -> logger.debug("$className  $sourceAnnoName 注解类型不正解")
                }
            }
    }

    private fun handleSingleMemberAnno(anno: SingleMemberAnnotationExpr) {
        typeDeclaration.tryAddImportToParentCompilationUnit(Schema::class.java)
        val pairs = NodeList<MemberValuePair>()
        pairs.add(MemberValuePair("name", anno.memberValue))
        val needAddAnno = NormalAnnotationExpr(Name(targetAnnoName), pairs)
        typeDeclaration.addAnnotation(needAddAnno)
        anno.remove()
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
                "value" -> pairs.add(MemberValuePair("name", it.value))
                "description" -> pairs.add(MemberValuePair("title", it.value))
                else -> logger.debug("$className  $sourceAnnoName 注解 $key 属性不支持转换到 $targetAnnoName 注解")
            }
        }
        typeDeclaration.tryAddImportToParentCompilationUnit(Schema::class.java)
        val needAddAnno = NormalAnnotationExpr(Name(targetAnnoName), pairs)
        typeDeclaration.addAnnotation(needAddAnno)
        anno.remove()
    }
}