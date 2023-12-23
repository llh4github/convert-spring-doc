package io.github.llh4github.sw3convert.core.convert

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.*
import io.swagger.annotations.ApiImplicitParams
import io.swagger.v3.oas.annotations.Parameters
import org.apache.logging.log4j.kotlin.Logging

/**
 *
 * Created At 2023/12/21 22:42
 * @author llh
 */
class ApiImplicitParamsToParameter(
    private val typeDeclaration: TypeDeclaration<*>
) : Logging, SwAnnoConvert {
    private val className: String by lazy { typeDeclaration.name.asString() }
    private val sourceAnnoName: String = ApiImplicitParams::class.simpleName!!
    private val targetAnnoName: String = Parameters::class.simpleName!!
    override fun convert() {
        typeDeclaration.methods.forEach { method ->
            method.annotations.filter { it.name.asString() == sourceAnnoName }
                .forEach {
                    when (it) {
                        is SingleMemberAnnotationExpr -> singleMemberAnno(it, method)
                        is NormalAnnotationExpr -> normalAnnotation(it, method)
                        else -> logger.debug("$className ${method.name.asString()} 方法  $sourceAnnoName 注解类型不正解")
                    }
                }
        }
    }

    private fun singleMemberAnno(anno: SingleMemberAnnotationExpr, method: MethodDeclaration) {
        val memberValue = anno.memberValue
        val annoList = NodeList<Expression>()
        if (memberValue is ArrayInitializerExpr) {
            memberValue.values.forEach {
                if (it is NormalAnnotationExpr) {
                    val pairs = handleApiImplicitParamProperties(it.pairs)
                    val needAdd = NormalAnnotationExpr(Name(targetAnnoName), pairs)
                    annoList.add(needAdd)
                } else {
                    logger.error(
                        "$className#${method.name.asString()}方法的 " +
                                "$sourceAnnoName 注解属性类型不正确： $memberValue"
                    )
                }
            }
            if (annoList.isNonEmpty) {
                val annoNeedAdd = SingleMemberAnnotationExpr(Name(targetAnnoName), ArrayInitializerExpr(annoList))
                method.addAnnotation(annoNeedAdd)
            }
            anno.remove()
        } else {
            logger.error(
                "$className#${method.name.asString()}方法的 " +
                        "$sourceAnnoName 注解属性类型不正确： $memberValue"
            )
        }
    }

    private fun normalAnnotation(anno: NormalAnnotationExpr, method: MethodDeclaration) {
        val subAnno = NodeList<MemberValuePair>()
        anno.pairs.filter { it.name.asString() == "value" }.forEach {
            if (it.value.isArrayInitializerExpr) {
                val list = NodeList<Expression>()
                (it.value as ArrayInitializerExpr).values.forEach { ele ->
                    val innerAnno = (ele as NormalAnnotationExpr)
                    val pairs = handleApiImplicitParamProperties(innerAnno.pairs)
                    val needAdd = NormalAnnotationExpr(Name("Parameter"), pairs)
                    list.add(needAdd)
                }
                subAnno.add(MemberValuePair("value", ArrayInitializerExpr(list)))
            }
        }
        method.addAnnotation(NormalAnnotationExpr(Name(targetAnnoName), subAnno))
        anno.remove()
    }
}