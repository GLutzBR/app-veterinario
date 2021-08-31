package br.com.lutztechnology.appveterinario.api.controller;

import br.com.lutztechnology.appveterinario.api.dto.EmployeeDTO;
import br.com.lutztechnology.appveterinario.api.hateoas.EmployeeAssembler;
import br.com.lutztechnology.appveterinario.model.Employee;
import br.com.lutztechnology.appveterinario.services.EmployeeService;
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
@RequestMapping("/api/v1/users")
public class EmployeeApiController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeAssembler employeeAssembler;

    @Autowired
    private PagedResourcesAssembler<Employee> pagedResourcesAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Employee>> searchAll(Pageable pageable) {
        Page<Employee> employees = employeeService.searchAll(pageable);

        return pagedResourcesAssembler.toModel(employees, employeeAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<Employee> searchById(@PathVariable Long id) {
        Employee employee = employeeService.searchById(id);

        return employeeAssembler.toModel(employee);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<Employee> insert(@RequestBody @Valid EmployeeDTO employeeDTO) {
        Employee employee = employeeService.insert(employeeDTO);

        return employeeAssembler.toModel(employee);
    }

    @PutMapping("/{id}")
    public EntityModel<Employee> update(
            @RequestBody @Valid EmployeeDTO employeeDTO,
            @PathVariable Long id) {
        Employee employee = employeeService.update(employeeDTO, id);

        return employeeAssembler.toModel(employee);
    }

    @PutMapping("/{id}/change-availability")
    public EntityModel<Employee> changeAvailability(@PathVariable Long id) {
        Employee employee = employeeService.changeAvailability(id);

        return employeeAssembler.toModel(employee);
    }
}
