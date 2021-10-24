package br.com.lutztechnology.appveterinario.api.hateoas;

import br.com.lutztechnology.appveterinario.api.controller.MedicalRecordApiController;
import br.com.lutztechnology.appveterinario.model.MedicalRecord;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MedicalRecordAssembler implements SimpleRepresentationModelAssembler<MedicalRecord> {
    @Override
    public void addLinks(EntityModel<MedicalRecord> resource) {
        Long id = resource.getContent().getId();

        Link selfLink = linkTo(methodOn(MedicalRecordApiController.class).searchById(id))
                .withSelfRel()
                .withType("GET")
                .withTitle("Medical Record details");

        Link archiveLink = linkTo(methodOn(MedicalRecordApiController.class).insert(null))
                .withSelfRel()
                .withType("PUT")
                .withTitle("Archive medical record");

        resource.add(selfLink, archiveLink);
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<MedicalRecord>> resources) {
        Link selfLink = linkTo(methodOn(MedicalRecordApiController.class).searchAll(null))
                .withSelfRel()
                .withType("GET")
                .withTitle("List Medical Records");

        resources.add(selfLink);
    }
}
