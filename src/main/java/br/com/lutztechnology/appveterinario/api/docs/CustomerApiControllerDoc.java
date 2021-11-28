package br.com.lutztechnology.appveterinario.api.docs;

import br.com.lutztechnology.appveterinario.api.annotations.ApiPageable;
import br.com.lutztechnology.appveterinario.api.dto.CustomerDTO;
import br.com.lutztechnology.appveterinario.api.exceptions.ApiError;
import br.com.lutztechnology.appveterinario.api.exceptions.ValidateApiError;
import br.com.lutztechnology.appveterinario.model.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Clientes", description = "Clientes Controller")
public interface CustomerApiControllerDoc {

    @ApiOperation(value = "Listar todos clientes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem de clientes realizadas com sucesso")
    })
    @ApiPageable
    CollectionModel<EntityModel<Customer>> searchAll(@ApiIgnore Pageable pageable);

    @ApiOperation(value = "Buscar cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado", response = ApiError.class)
    })
    EntityModel<Customer> searchById(Long id);

    @ApiOperation(value = "Cadastrar cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cliente cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Houveram erros de validação", response = ValidateApiError.class)
    })
    EntityModel<Customer> insert(CustomerDTO customerDTO);

    @ApiOperation(value = "Atualizar cliente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Cliente atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Houveram erros de validação", response = ValidateApiError.class),
            @ApiResponse(code = 404, message = "Cliente não encontrado", response = ApiError.class)

    })
    EntityModel<Customer> update(CustomerDTO customerDTO, Long id);

    @ApiOperation(value = "Excluir cliente por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Cliente excluído com sucesso"),
            @ApiResponse(code = 404, message = "Cliente não encontrado", response = ApiError.class)
    })
    ResponseEntity<?> deleteById(Long id);
}
