package io.github.llh4github.sw3convert.core.convert

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.*
import org.apache.logging.log4j.kotlin.Logging


/**
 *
 * Created At 2023/12/21 22:42
 * @author llh
 */
class ApiResponsesConvert(
    private val typeDeclaration: TypeDeclaration<*>
) : SwAnnoConvert, Logging {
    private val className: String by lazy { typeDeclaration.name.asString() }
    private val sourceAnnoName: String = ApiResponses2::class.simpleName!!
    private val targetAnnoName: String = sourceAnnoName
    private val apiResponse3: String = ApiResponse3::class.simpleName!!

    override fun convert() {
        typeDeclaration.methods.forEach { method ->
            method.annotations.filter { it.name.asString() == sourceAnnoName }
                .forEach {
                    when (it) {
                        is SingleMemberAnnotationExpr -> singleMemberAnno(it, method)
                        is NormalAnnotationExpr -> normalAnnotation(it, method)
                        else -> logger.debug("$className  $sourceAnnoName 注解类型不正解")
                    }
                }
        }
    }

    private fun singleMemberAnno(anno: SingleMemberAnnotationExpr, method: MethodDeclaration) {
        val subAnno = NodeList<MemberValuePair>()
        val list = NodeList<Expression>()
        (anno.memberValue as ArrayInitializerExpr).values
            .forEach {
                if (it is NormalAnnotationExpr) {
                    val properties = handleApiResponseProperties(it)
                    val innerAnno = NormalAnnotationExpr(Name(apiResponse3), properties)
                    list.add(innerAnno)
                }
            }
        subAnno.add(MemberValuePair("value", ArrayInitializerExpr(list)))

        method.addAnnotation(NormalAnnotationExpr(Name(targetAnnoName), subAnno))
        typeDeclaration.tryAddImportToParentCompilationUnit(ApiResponse3::class.java)
        typeDeclaration.tryAddImportToParentCompilationUnit(ApiResponses3::class.java)
        anno.remove()
    }

    private fun normalAnnotation(anno: NormalAnnotationExpr, method: MethodDeclaration) {
        val subAnno = NodeList<MemberValuePair>()
        val list = NodeList<Expression>()
        anno.pairs.filter { it.name.asString() == "value" }
            .forEach {
                if (it is MemberValuePair) {
                    val values = it.value.asArrayInitializerExpr().values
                    values.forEach { ele ->
                        val properties = handleApiResponseProperties(ele as NormalAnnotationExpr)
                        val innerAnno = NormalAnnotationExpr(Name(apiResponse3), properties)
                        list.add(innerAnno)
                    }
                } else {
                    logger.debug("? $it")
                }
            }
        subAnno.add(MemberValuePair("value", ArrayInitializerExpr(list)))
        val targetAnno = NormalAnnotationExpr(Name(targetAnnoName), subAnno)
        method.addAnnotation(targetAnno)
        typeDeclaration.tryAddImportToParentCompilationUnit(ApiResponse3::class.java)
        typeDeclaration.tryAddImportToParentCompilationUnit(ApiResponses3::class.java)
        anno.remove()
    }
}