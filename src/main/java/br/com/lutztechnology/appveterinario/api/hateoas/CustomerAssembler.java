package br.com.lutztechnology.appveterinario.api.hateoas;

import br.com.lutztechnology.appveterinario.api.controller.CustomerApiController;
import br.com.lutztechnology.appveterinario.model.Customer;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerAssembler implements SimpleRepresentationModelAssembler<Customer> {
    @Override
    public void addLinks(EntityModel<Customer> resource) {
        Long id = resource.getContent().getId();

        Link selfLink = linkTo(methodOn(CustomerApiController.class).searchById(id))
                .withSelfRel()
                .withType("GET")
                .withTitle("Customer details");

        Link updateLink = linkTo(methodOn(CustomerApiController.class).update(null, id))
                .withSelfRel()
                .withType("PUT")
                .withTitle("Update customer");

        Link deleteLink = linkTo(methodOn(CustomerApiController.class).deleteById(id))
                .withSelfRel()
                .withType("DELETE")
                .withTitle("Delete customer");
        resource.add(selfLink, updateLink, deleteLink);
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Customer>> resources) {
        Link selfLink = linkTo(methodOn(CustomerApiController.class).searchAll(null))
                .withSelfRel()
                .withType("GET")
                .withTitle("List customers");

        resources.add(selfLink);
    }
}
