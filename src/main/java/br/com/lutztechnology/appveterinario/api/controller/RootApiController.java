package br.com.lutztechnology.appveterinario.api.controller;

import br.com.lutztechnology.appveterinario.api.hateoas.RootModel;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1")
public class RootApiController {

    @GetMapping
    public RootModel root() {
        RootModel rootModel = new RootModel();

        Link rolesListLink = linkTo(methodOn(RoleApiController.class).searchAll(null))
                .withRel("roles")
                .withType("GET")
                .withTitle("List roles");

        Link roleInsertLink = linkTo(methodOn(RoleApiController.class).insert(null))
                .withRel("roles")
                .withType("POST")
                .withTitle("Insert new role");

        Link employeesListLink = linkTo(methodOn(EmployeeApiController.class).searchAll(null))
                .withRel("employees")
                .withType("GET")
                .withTitle("List employees");

        Link employeeInsertLink = linkTo(methodOn(EmployeeApiController.class).insert(null))
                .withRel("employees")
                .withType("POST")
                .withTitle("Insert new employee");

        Link customersListLink = linkTo(methodOn(CustomerApiController.class).searchAll(null))
                .withRel("customers")
                .withType("GET")
                .withTitle("List customers");

        Link customerInsertLink = linkTo(methodOn(CustomerApiController.class).insert(null))
                .withRel("customers")
                .withType("POST")
                .withTitle("Insert new customer");

        Link animalListLink = linkTo(methodOn(AnimalApiController.class).searchAll(null))
                .withRel("animals")
                .withType("GET")
                .withTitle("List animals");

        Link animalInsertLink = linkTo(methodOn(AnimalApiController.class).insert(null))
                .withRel("animals")
                .withType("POST")
                .withTitle("Insert animals");

        rootModel.add(rolesListLink,
                roleInsertLink,
                employeesListLink,
                employeeInsertLink,
                customersListLink,
                customerInsertLink,
                animalListLink,
                animalInsertLink
        );

        return rootModel;
    }
}
