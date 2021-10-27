package br.com.lutztechnology.appveterinario.api.hateoas;

import br.com.lutztechnology.appveterinario.api.controller.RoleApiController;
import br.com.lutztechnology.appveterinario.model.Role;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleAssembler implements SimpleRepresentationModelAssembler<Role> {
    @Override
    public void addLinks(EntityModel<Role> resource) {
        Long id = resource.getContent().getId();

        Link selfLink = linkTo(methodOn(RoleApiController.class).searchById(id))
                .withSelfRel()
                .withType("GET")
                .withTitle("Role details");

        Link updateLink = linkTo(methodOn(RoleApiController.class).update(null, id))
                .withSelfRel()
                .withType("PUT")
                .withTitle("Update role");

        Link deleteLink = linkTo(methodOn(RoleApiController.class).deleteById(id))
                .withSelfRel()
                .withType("DELETE")
                .withTitle("Delete role");

        resource.add(selfLink, updateLink, deleteLink);
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Role>> resources) {
        Link selfLink = linkTo(methodOn(RoleApiController.class).searchAll(null))
                .withSelfRel()
                .withType("GET")
                .withTitle("List roles");

        resources.add(selfLink);
    }
}
