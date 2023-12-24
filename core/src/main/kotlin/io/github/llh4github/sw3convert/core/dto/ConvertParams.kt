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
     * 是否覆盖原文件
     */
    val overrideFile: Boolean = false,
    /**
     * 复制到新目录。仅在[overrideFile]属性启用的情况下使用。
     */
    val targetDir: String? = null,
)
