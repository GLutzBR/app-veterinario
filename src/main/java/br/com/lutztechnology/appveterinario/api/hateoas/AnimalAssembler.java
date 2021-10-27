package br.com.lutztechnology.appveterinario.api.hateoas;

import br.com.lutztechnology.appveterinario.api.controller.AnimalApiController;
import br.com.lutztechnology.appveterinario.model.Animal;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AnimalAssembler implements SimpleRepresentationModelAssembler<Animal> {
    @Override
    public void addLinks(EntityModel<Animal> resource) {
        Long id = resource.getContent().getId();

        Link selfLink = linkTo(methodOn(AnimalApiController.class).searchById(id))
                .withSelfRel()
                .withType("GET")
                .withTitle("Animal details");

        Link updateLink = linkTo(methodOn(AnimalApiController.class).update(null, id))
                .withSelfRel()
                .withType("PUT")
                .withTitle("Update animal");

        Link deleteLink = linkTo(methodOn(AnimalApiController.class).deleteById(id))
                .withSelfRel()
                .withType("DELETE")
                .withTitle("Delete animal");

        resource.add(selfLink, updateLink, deleteLink);
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Animal>> resources) {
        Link selfLink = linkTo(methodOn(AnimalApiController.class).searchAll(null))
                .withSelfRel()
                .withType("GET")
                .withTitle("List Animals");

        resources.add(selfLink);
    }
}
