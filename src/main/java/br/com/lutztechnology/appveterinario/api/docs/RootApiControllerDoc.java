package br.com.lutztechnology.appveterinario.api.docs;

import br.com.lutztechnology.appveterinario.api.hateoas.RootModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Raiz", description = "Raiz Controller")
public interface RootApiControllerDoc {

    @ApiOperation(value = "Listar os principais endpoints do projeto")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem de endpoints realizada com sucesso.")
    })
    RootModel root();
}
