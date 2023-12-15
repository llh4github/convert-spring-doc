package io.github.llh4github.convertspringdoc.dto

/**
 *
 *
 * Created At 2023/12/15 18:14
 * @author llh
 */
data class ConvertResult(
    val target: Swagger3Anno,
    val children: List<Swagger3Anno> = emptyList(),
    val childrenPropertyName: String = "",
)
