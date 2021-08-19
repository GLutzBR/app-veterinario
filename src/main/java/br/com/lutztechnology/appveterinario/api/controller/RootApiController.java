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

        Link rolesLink = linkTo(methodOn(RoleApiController.class).searchAll(null))
                .withRel("roles")
                .withType("GET")
                .withTitle("List roles");

        Link insertLink = linkTo(methodOn(RoleApiController.class).insert(null))
                .withRel("roles")
                .withType("POST")
                .withTitle("Insert new role");

        rootModel.add(rolesLink, insertLink);

        return rootModel;
    }
}
