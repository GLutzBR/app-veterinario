package br.com.lutztechnology.appveterinario.api.hateoas;

import br.com.lutztechnology.appveterinario.api.controller.EmployeeApiController;
import br.com.lutztechnology.appveterinario.model.Employee;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class EmployeeAssembler implements SimpleRepresentationModelAssembler<Employee> {
    @Override
    public void addLinks(EntityModel<Employee> resource) {
        Long id = resource.getContent().getId();

        Link selfLink = linkTo(methodOn(EmployeeApiController.class).searchById(id))
                .withSelfRel()
                .withType("GET")
                .withTitle("Employee details");

        Link updateLink = linkTo(methodOn(EmployeeApiController.class).update(null, id))
                .withSelfRel()
                .withType("PUT")
                .withTitle("Update employee");

        Link archiveLink = linkTo(methodOn(EmployeeApiController.class).changeAvailability(id))
                .withSelfRel()
                .withType("PUT")
                .withTitle("Disable/Enable employee user");

        resource.add(selfLink, updateLink, archiveLink);
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<Employee>> resources) {
        Link selfLink = linkTo(methodOn(EmployeeApiController.class).searchAll(null))
                .withSelfRel()
                .withType("GET")
                .withTitle("List all employees");

        resources.add(selfLink);
    }
}