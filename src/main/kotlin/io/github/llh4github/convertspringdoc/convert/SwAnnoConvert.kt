package io.github.llh4github.convertspringdoc.convert

/**
 * Swagger注解转换
 *
 * Created At 2023/12/15 17:24
 * @author llh
 */
interface SwAnnoConvert {

    /**
     * 将swagger2注解转为swagger3注解
     *
     * 由具体类实现转换逻辑
     */
    fun convert()
}