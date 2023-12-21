package io.github.llh4github.convertspringdoc.convert

import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.MethodDeclaration
import com.github.javaparser.ast.body.TypeDeclaration
import com.github.javaparser.ast.expr.MemberValuePair
import com.github.javaparser.ast.expr.Name
import com.github.javaparser.ast.expr.NormalAnnotationExpr
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr
import io.swagger.annotations.ApiOperation
import io.swagger.v3.oas.annotations.Operation
import org.apache.logging.log4j.kotlin.Logging

/**
 *
 * Created At 2023/12/21 21:36
 * @author llh
 */
class ApiOperationToOperation(private val typeDeclaration: TypeDeclaration<*>) : Logging {


    private val className: String by lazy { typeDeclaration.name.asString() }
    private val sourceAnnoName: String = ApiOperation::class.simpleName!!
    private val targetAnnoName: String = Operation::class.simpleName!!

    fun convert() {
        val list = typeDeclaration.methods
        list.forEach { method ->
            method.annotations.filter { it.name.asString() == sourceAnnoName }
                .forEach {
                    when (it) {
                        is SingleMemberAnnotationExpr -> singleAnno(it, method)
                        is NormalAnnotationExpr -> normalAnnotation(it, method)
                        else -> logger.debug("$className 无 $sourceAnnoName 注解")
                    }
                }
        }
    }

    private fun singleAnno(anno: SingleMemberAnnotationExpr, method: MethodDeclaration) {
        val value = anno.memberValue
        typeDeclaration.tryAddImportToParentCompilationUnit(Operation::class.java)
        val needAdd = SingleMemberAnnotationExpr(Name(targetAnnoName), value)
        method.addAnnotation(needAdd)
        anno.remove()
        logger.debug("$className#${method.nameAsString} 注解 $anno 替换为 $needAdd")
    }

    private fun normalAnnotation(anno: NormalAnnotationExpr, method: MethodDeclaration) {
        val list = anno.pairs
        val pairs = NodeList<MemberValuePair>()
        list.forEach {
            when (val name = it.name.asString()) {
                "value" -> pairs.add(MemberValuePair("summary", it.value))
                "notes" -> pairs.add(MemberValuePair("description", it.value))
                "tags" -> pairs.add(MemberValuePair("tags", it.value))
                "hidden" -> pairs.add(MemberValuePair("hidden", it.value))
                "ignoreJsonView" -> pairs.add(MemberValuePair("ignoreJsonView", it.value))
                "httpMethod" -> pairs.add(MemberValuePair("method", it.value))
                else -> logger.debug("$sourceAnnoName 注解的 $name 在 $targetAnnoName 注解中无对应属性")
            }
        }

        val tagsAnno = NormalAnnotationExpr(Name(targetAnnoName), pairs)
        method.addAnnotation(tagsAnno)
        anno.remove()
    }
}