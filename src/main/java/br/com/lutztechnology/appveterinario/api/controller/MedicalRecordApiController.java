package br.com.lutztechnology.appveterinario.api.controller;

import br.com.lutztechnology.appveterinario.api.dto.MedicalRecordDTO;
import br.com.lutztechnology.appveterinario.api.hateoas.MedicalRecordAssembler;
import br.com.lutztechnology.appveterinario.model.MedicalRecord;
import br.com.lutztechnology.appveterinario.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/medical-records")
public class MedicalRecordApiController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private MedicalRecordAssembler medicalRecordAssembler;

    @Autowired
    private PagedResourcesAssembler<MedicalRecord> pagedResourcesAssembler;

    @GetMapping
    public CollectionModel<EntityModel<MedicalRecord>> searchAll(Pageable pageable) {
        Page<MedicalRecord> medicalRecords = medicalRecordService.searchAll(pageable);

        return pagedResourcesAssembler.toModel(medicalRecords, medicalRecordAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<MedicalRecord> searchById(@PathVariable Long id) {
        MedicalRecord medicalRecord = medicalRecordService.searchById(id);

        return medicalRecordAssembler.toModel(medicalRecord);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<MedicalRecord> insert(@RequestBody @Valid MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = medicalRecordService.insert(medicalRecordDTO);

        return medicalRecordAssembler.toModel(medicalRecord);
    }

    @PutMapping("/{id}/archive")
    public EntityModel<MedicalRecord> archive(@PathVariable Long id) {
        MedicalRecord medicalRecord = medicalRecordService.archive(id);

        return medicalRecordAssembler.toModel(medicalRecord);
    }
}
