package io.github.llh4github.sw3convert.core.convert

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.*
import io.swagger.annotations.Api
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.apache.logging.log4j.kotlin.Logging

/**
 *
 *
 * Created At 2023/12/20 15:55
 * @author llh
 */
class ApiToTag(
    private val typeDeclaration: TypeDeclaration<*>
) : SwAnnoConvert, Logging {

    private val className: String by lazy { typeDeclaration.name.asString() }
    private val sourceAnnoName: String = Api::class.simpleName!!
    override fun convert() {
        val noAnno = typeDeclaration.annotations.count { it.name.asString() == sourceAnnoName } == 0
        if (noAnno) {
            logger.debug("$className 类没有目标注解: Api")
            return
        }
        typeDeclaration.annotations.filter { it.name.toString() == "Api" }
            .forEach {
                when (it) {
                    is SingleMemberAnnotationExpr -> singleAnno(it)
                    is NormalAnnotationExpr -> normalAnnotation(it)
                    else -> logger.error("$className  $sourceAnnoName 注解类型不正解")
                }
            }

    }

    private fun singleAnno(anno: SingleMemberAnnotationExpr) {
        val value = anno.memberValue
        typeDeclaration.tryAddImportToParentCompilationUnit(Tag::class.java)
        val needAdd = SingleMemberAnnotationExpr(Name("Tag"), value)
        typeDeclaration.addAnnotation(needAdd)
        anno.remove()
        logger.debug("$className 注解 $anno 替换为 $needAdd")
    }

    private fun normalAnnotation(anno: NormalAnnotationExpr) {
        val list = anno.pairs
        handleHiddenProperty(list)
        handleValueAndTagsProperty(list)
        anno.remove()
    }

    private fun handleValueProperty(
        pairs: NodeList<MemberValuePair>
    ): SingleMemberAnnotationExpr? {
        val valuePairs = pairs.firstOrNull { it.name.asString() == "value" }
        if (valuePairs == null) {
            logger.debug("$className $sourceAnnoName 注解无 value属性")
            return null
        }
        val value = valuePairs.value
        typeDeclaration.tryAddImportToParentCompilationUnit(Tag::class.java)
        return SingleMemberAnnotationExpr(Name("Tag"), value)
    }

    private fun handleValueAndTagsProperty(pairs: NodeList<MemberValuePair>) {
        val tagAnno = handleValueProperty(pairs)
        val tagsPairs = pairs.firstOrNull { it.name.asString() == "tags" }
        if (tagsPairs == null) {
            logger.debug("$className $sourceAnnoName 注解无 tags 属性")
            tagAnno?.let { typeDeclaration.addAnnotation(it) }
        } else {
            typeDeclaration.tryAddImportToParentCompilationUnit(Tags::class.java)
            val nodeList: NodeList<Expression> = NodeList()
            when (tagsPairs.value) {
                is StringLiteralExpr -> {
                    val anno = SingleMemberAnnotationExpr(Name("Tag"), tagsPairs.value)
                    nodeList.add(anno)
                }

                is ArrayInitializerExpr -> {
                    val a = tagsPairs.value as ArrayInitializerExpr
                    val list = a.values.map { SingleMemberAnnotationExpr(Name("Tag"), it) }
                        .toList()
                    nodeList.addAll(list)
                }

                else -> logger.error(
                    "$className @Api中的tags属性类型未知 ${
                        tagsPairs.value.asStringLiteralExpr().asString()
                    }"
                )
            }
            val pairs2 = NodeList<MemberValuePair>()

            tagAnno?.let { nodeList.add(it) }
            val arr = ArrayInitializerExpr(nodeList)
            pairs2.add(MemberValuePair("value", arr))
            val tagsAnno = NormalAnnotationExpr(Name("Tags"), pairs2)
            typeDeclaration.addAnnotation(tagsAnno)
        }
    }

    private fun handleHiddenProperty(pairs: NodeList<MemberValuePair>) {
        pairs.find { it.name.toString() == "hidden" }?.let {
            val value = it.value.asBooleanLiteralExpr()
            if (value.value) {
                typeDeclaration.tryAddImportToParentCompilationUnit(Hidden::class.java)
                val needAdd = MarkerAnnotationExpr(Name("Hidden"))
                typeDeclaration.addAnnotation(needAdd)
            } else {
                logger.debug("$className Api注解 hidden属性值为 $value")
            }
        }
    }


}