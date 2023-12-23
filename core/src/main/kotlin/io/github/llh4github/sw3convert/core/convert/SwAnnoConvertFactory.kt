package io.github.llh4github.sw3convert.core.convert

import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.TypeDeclaration

/**
 * 注解转化工厂，外部应调用此类的方法
 *
 * Created At 2023/12/23 10:32
 * @author llh
 */
object SwAnnoConvertFactory {
    fun convert(parseResult: CompilationUnit): CompilationUnit {
        removeSw2Import(parseResult.imports)
        parseResult.types.forEach { annoConvert(it) }
       return parseResult
    }

    private fun annoConvert(typeDeclaration: TypeDeclaration<*>) {
        ApiImplicitParamToParameter(typeDeclaration).convert()
        ApiImplicitParamsToParameter(typeDeclaration).convert()
        ApiModelPropertyToSchema(typeDeclaration).convert()
        ApiModelToSchema(typeDeclaration).convert()
        ApiOperationToOperation(typeDeclaration).convert()
        ApiResponseConvert(typeDeclaration).convert()
        ApiToTag(typeDeclaration).convert()
    }

}