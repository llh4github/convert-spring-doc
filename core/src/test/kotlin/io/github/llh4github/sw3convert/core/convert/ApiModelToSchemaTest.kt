package io.github.llh4github.sw3convert.core.convert

import org.junit.jupiter.api.Test


/**
 *
 * Created At 2023/12/23 16:54
 * @author llh
 */
class ApiModelToSchemaTest : BaseTest() {
    init {
        testcase.put(
            """
           import io.swagger.annotations.ApiModel;
           @ApiModel()
           public class Demo{} 
       """.trimIndent(), """
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema()
public class Demo {
}
       """.trimIndent()
        )

        testcase.put(
            """
import io.swagger.annotations.ApiModel;
@ApiModel(value = "a")
public class Demo {
}
        """.trimIndent(), """
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "a")
public class Demo {
}
""".trimIndent()
        )
        testcase.put(
            """
import io.swagger.annotations.ApiModel;
@ApiModel("a")
public class Demo {
}
        """.trimIndent(), """
import io.swagger.annotations.ApiModel;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "a")
public class Demo {
}
""".trimIndent()
        )
    }

    @Test
    fun test() {
        runConvertTest { ApiModelToSchema(it).convert() }
    }
}