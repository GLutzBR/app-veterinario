package br.com.lutztechnology.appveterinario.api.controller;

import br.com.lutztechnology.appveterinario.api.docs.RoleApiControllerDoc;
import br.com.lutztechnology.appveterinario.api.dto.RoleDTO;
import br.com.lutztechnology.appveterinario.api.hateoas.RoleAssembler;
import br.com.lutztechnology.appveterinario.model.Role;
import br.com.lutztechnology.appveterinario.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleApiController implements RoleApiControllerDoc {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleAssembler roleAssembler;

    @Autowired
    private PagedResourcesAssembler<Role> pagedResourcesAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Role>> searchAll(Pageable pageable) {
        Page<Role> roles = roleService.searchAll(pageable);

        return pagedResourcesAssembler.toModel(roles, roleAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<Role> searchById(@PathVariable Long id) {
        Role role = roleService.searchById(id);

        return roleAssembler.toModel(role);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<Role> insert(@RequestBody @Valid RoleDTO roleDTO) {
        Role role = roleService.insert(roleDTO);

        return roleAssembler.toModel(role);
    }

    @PutMapping("/{id}")
    public EntityModel<Role> update(
            @RequestBody @Valid RoleDTO roleDTO,
            @PathVariable Long id) {
        Role role = roleService.update(roleDTO, id);

        return roleAssembler.toModel(role);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        roleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
