package br.com.lutztechnology.appveterinario.api.docs;

import br.com.lutztechnology.appveterinario.api.annotations.ApiPageable;
import br.com.lutztechnology.appveterinario.api.dto.AnimalDTO;
import br.com.lutztechnology.appveterinario.api.exceptions.ApiError;
import br.com.lutztechnology.appveterinario.api.exceptions.ValidateApiError;
import br.com.lutztechnology.appveterinario.model.Animal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Animais", description = "Animais Controller")
public interface AnimalApiControllerDoc {

    @ApiOperation(value = "Listar todos animais")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem de animais realizadas com sucesso")
    })
    @ApiPageable
    CollectionModel<EntityModel<Animal>> searchAll(@ApiIgnore Pageable pageable);

    @ApiOperation(value = "Buscar animal por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Animal encontrado com sucesso"),
            @ApiResponse(code = 404, message = "Animal não encontrado", response = ApiError.class)
    })
    EntityModel<Animal> searchById(Long id);

    @ApiOperation(value = "Cadastrar animal")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Animal cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Houveram erros de validação", response = ValidateApiError.class)
    })
    EntityModel<Animal> insert(AnimalDTO animalDTO);

    @ApiOperation(value = "Atualizar animal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Animal atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Houveram erros de validação", response = ValidateApiError.class),
            @ApiResponse(code = 404, message = "Animal não encontrado", response = ApiError.class)

    })
    EntityModel<Animal> update(AnimalDTO animalDTO, Long id);

    @ApiOperation(value = "Excluir animal por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Animal excluído com sucesso"),
            @ApiResponse(code = 404, message = "Animal não encontrado", response = ApiError.class)
    })
    ResponseEntity<?> deleteById(Long id);
}
