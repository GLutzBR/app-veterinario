package br.com.lutztechnology.appveterinario.api.docs;

import br.com.lutztechnology.appveterinario.api.annotations.ApiPageable;
import br.com.lutztechnology.appveterinario.api.dto.MedicalRecordDTO;
import br.com.lutztechnology.appveterinario.api.exceptions.ApiError;
import br.com.lutztechnology.appveterinario.api.exceptions.ValidateApiError;
import br.com.lutztechnology.appveterinario.model.MedicalRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Prontuarios", description = "Prontuários Controller")
public interface MedicalRecordApiControllerDoc {

    @ApiOperation(value = "Listar todos os Prontuários")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Listagem de prontuários realizado com sucesso.")
    })
    @ApiPageable
    CollectionModel<EntityModel<MedicalRecord>> searchAll(@ApiIgnore Pageable pageable);

    @ApiOperation(value = "Buscar prontuário por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Prontuário encontrado com sucesso."),
            @ApiResponse(code = 404, message = "Prontuário não encontrado", response = ApiError.class)
    })
    EntityModel<MedicalRecord> searchById(Long id);

    @ApiOperation(value = "Registrar prontuário")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Prontuário registrado com sucesso."),
            @ApiResponse(code = 400, message = "Houveram erros de validação.", response = ValidateApiError.class)
    })
    EntityModel<MedicalRecord> insert(MedicalRecordDTO medicalRecordDTO);

    @ApiOperation(value = "Arquivar prontuário")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Prontuário arquivado com sucesso."),
            @ApiResponse(code = 404, message = "Prontuário não encontrado", response = ApiError.class)
    })
    EntityModel<MedicalRecord> archive(Long id);
}
