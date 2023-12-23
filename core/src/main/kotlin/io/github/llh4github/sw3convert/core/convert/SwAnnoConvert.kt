package io.github.llh4github.sw3convert.core.convert

/**
 *
 * Created At 2023/12/23 15:56
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