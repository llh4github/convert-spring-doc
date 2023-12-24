package io.github.llh4github.sw3convert.core.convert

import com.github.javaparser.ast.ImportDeclaration
import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.expr.MemberValuePair
import org.apache.logging.log4j.kotlin.logger


private val logger = logger("commons")
internal fun handleApiImplicitParamProperties(pairs: NodeList<MemberValuePair>): NodeList<MemberValuePair> {
    val rs = NodeList<MemberValuePair>()
    pairs.forEach {
        when (val name = it.name.asString()) {
            "name" -> rs.add(MemberValuePair("name", it.value))
            "value" -> rs.add(MemberValuePair("description", it.value))
            "defaultValue" -> rs.add(MemberValuePair("example", it.value))
            "required" -> rs.add(MemberValuePair("required", it.value))
            else -> logger.debug("ApiImplicitParam注解的 $name 在Parameter注解中无对应属性")
        }
    }
    return rs
}

internal fun removeSw2Import(imports: NodeList<ImportDeclaration>): NodeList<ImportDeclaration> {
    return imports
        .filter { !it.name.asString().contains("io.swagger.annotations") }
        .toCollection(NodeList())
}
