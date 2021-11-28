package br.com.lutztechnology.appveterinario.api.annotations;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({
        ElementType.METHOD,
        ElementType.ANNOTATION_TYPE,
        ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(name = "page", dataType = "int", paramType = "query", defaultValue = "1", value = "Número da página"),
        @ApiImplicitParam(name = "size", dataType = "int", paramType = "query", defaultValue = "20", value = "Quantidade de elementos por página"),
        @ApiImplicitParam(name = "sort", dataType = "string", paramType = "query", value = "Critério de ordenação no formato: propriedade(,asc|desc)", allowMultiple = true)
})
public @interface ApiPageable {
}
