package br.com.lutztechnology.appveterinario.api.docs;

import br.com.lutztechnology.appveterinario.api.annotations.ApiPageable;
import br.com.lutztechnology.appveterinario.api.dto.EmployeeDTO;
import br.com.lutztechnology.appveterinario.api.exceptions.ApiError;
import br.com.lutztechnology.appveterinario.api.exceptions.ValidateApiError;
import br.com.lutztechnology.appveterinario.model.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Funcionarios", description = "Funcionários Controller")
public interface EmployeeApiControllerDoc {

    @ApiOperation(value = "Listar todos os funcionários")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem de funcionários realizado com sucesso.")
    })
    @ApiPageable
    CollectionModel<EntityModel<Employee>> searchAll(@ApiIgnore Pageable pageable);

    @ApiOperation(value = "Buscar funcionário por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Funcionário encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Funcionário não encontrado", response = ApiError.class)
    })
    EntityModel<Employee> searchById(Long id);

    @ApiOperation(value = "Cadastrar funcionário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Funcionário cadastrado com sucesso."),
            @ApiResponse(code = 400, message = "Houveram erros de validação.", response = ValidateApiError.class)
    })
    EntityModel<Employee> insert(EmployeeDTO employeeDTO);

    @ApiOperation(value = "Atualizar funcionário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Funcionário atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Houveram erros de validação", response = ValidateApiError.class),
            @ApiResponse(code = 404, message = "Funcionário não encontrado", response = ApiError.class)

    })
    EntityModel<Employee> update(EmployeeDTO employeeDTO, Long id);

    @ApiOperation(value = "Habilitar/Desabilitar funcionário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Funcionário atualizado com sucesso."),
            @ApiResponse(code = 404, message = "Funcionário não encontrado", response = ApiError.class)
    })
    EntityModel<Employee> changeAvailability(Long id);
}
