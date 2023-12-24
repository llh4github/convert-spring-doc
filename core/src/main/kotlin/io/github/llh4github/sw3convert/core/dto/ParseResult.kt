package io.github.llh4github.sw3convert.core.dto

import com.github.javaparser.ast.CompilationUnit

/**
 *
 * Created At 2023/12/24 10:26
 * @author llh
 */
data class ParseResult(
    /**
     * 被解析后的AST
     */
    val ast: CompilationUnit,

    /**
     * 是否被修改过
     */
    val modified: Boolean = false,
)
