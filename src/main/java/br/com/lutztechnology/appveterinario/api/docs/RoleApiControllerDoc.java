package br.com.lutztechnology.appveterinario.api.docs;

import br.com.lutztechnology.appveterinario.api.annotations.ApiPageable;
import br.com.lutztechnology.appveterinario.api.dto.RoleDTO;
import br.com.lutztechnology.appveterinario.api.exceptions.ApiError;
import br.com.lutztechnology.appveterinario.api.exceptions.ValidateApiError;
import br.com.lutztechnology.appveterinario.model.Role;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Cargos", description = "Cargos Controller")
public interface RoleApiControllerDoc {

    @ApiOperation(value = "Listar todos os cargos")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem de cargos realizado com sucesso.")
    })
    @ApiPageable
    CollectionModel<EntityModel<Role>> searchAll(@ApiIgnore Pageable pageable);

    @ApiOperation(value = "Buscar cargo por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cargo encontrado com sucesso."),
            @ApiResponse(code = 404, message = "Cargo não encontrado", response = ApiError.class)
    })
    EntityModel<Role> searchById(Long id);

    @ApiOperation(value = "Cadastrar cargo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cargo cadastrado com sucesso."),
            @ApiResponse(code = 400, message = "Houveram erros de validação", response = ValidateApiError.class)
    })
    EntityModel<Role> insert(RoleDTO roleDTO);

    @ApiOperation(value = "Atualizar cargo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cargo atualizado com sucesso."),
            @ApiResponse(code = 400, message = "Houveram erros de validação", response = ValidateApiError.class),
            @ApiResponse(code = 404, message = "Cargo não encontrado", response = ApiError.class)
    })
    EntityModel<Role> update(RoleDTO roleDTO, Long id);

    @ApiOperation(value = "Excluir cargo por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Cargo excluído com sucesso."),
            @ApiResponse(code = 404, message = "Cargo não encontrado", response = ApiError.class)
    })
    ResponseEntity<?> deleteById(Long id);
}
