package io.github.llh4github.convertspringdoc.convert

import io.github.llh4github.convertspringdoc.dto.Swagger2Anno
import io.github.llh4github.convertspringdoc.dto.Swagger3Anno

/**
 *
 *
 * Created At 2023/12/15 17:24
 * @author llh
 */
interface DefaultConvert {

    fun convert(anno: Swagger2Anno): List<Swagger3Anno>
}