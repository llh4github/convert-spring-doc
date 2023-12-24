package io.github.llh4github.sw3convert.core.dto

/**
 *
 * Created At 2023/12/24 10:16
 * @author llh
 */
data class ConvertParams(
    /**
     * 原文件路径
     */
    val sourcePath: String,
    /**
     * 复制到新目录。
     */
    val targetDir: String? = null,
)
