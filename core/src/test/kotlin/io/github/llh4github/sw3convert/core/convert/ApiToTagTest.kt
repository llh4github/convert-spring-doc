package io.github.llh4github.sw3convert.core.convert

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

/**
 *
 * Created At 2023/12/24 16:24
 * @author llh
 */
class ApiToTagTest : BaseTest(){
    init {
        testcase.put("""
@Api(tags = {"后台接口", "角色接口"})
public class Demo{}
    """.trimIndent(),"""
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

@Tags(value = { @Tag(name = "后台接口"), @Tag(name = "角色接口") })
public class Demo {
}
    """.trimIndent())
        //
    }

    @Test
    fun test(){
        runConvertTest { ApiToTag(it).convert() }
    }
}