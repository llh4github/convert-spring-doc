package io.github.llh4github.convertspringdoc.dto.sw3

import io.github.llh4github.convertspringdoc.dto.Sw3AnnoInfo
import io.swagger.v3.oas.annotations.tags.Tag
import kotlin.reflect.KClass

/**
 *
 *
 * Created At 2023/12/15 17:14
 * @author llh
 */
class TagAnno : Sw3AnnoInfo<Tag>() {
    override val anno: KClass<Tag>
        get() = Tag::class
    override val defaultPropertyName: String
        get() = "name"
}