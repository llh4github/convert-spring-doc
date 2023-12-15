package io.github.llh4github.convertspringdoc.dto.sw2

import io.github.llh4github.convertspringdoc.dto.Sw2AnnoInfo
import io.swagger.annotations.Api
import kotlin.reflect.KClass

/**
 *
 *
 * Created At 2023/12/15 16:13
 * @author llh
 */
class ApiAnno : Sw2AnnoInfo<Api>() {
    override val anno: KClass<Api> = Api::class
    override val defaultPropertyName: String = "value"
}
