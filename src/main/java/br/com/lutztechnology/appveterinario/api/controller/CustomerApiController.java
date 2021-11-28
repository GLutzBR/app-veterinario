package br.com.lutztechnology.appveterinario.api.controller;

import br.com.lutztechnology.appveterinario.api.docs.CustomerApiControllerDoc;
import br.com.lutztechnology.appveterinario.api.dto.CustomerDTO;
import br.com.lutztechnology.appveterinario.api.hateoas.CustomerAssembler;
import br.com.lutztechnology.appveterinario.model.Customer;
import br.com.lutztechnology.appveterinario.services.CustomerService;
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
@RequestMapping("/api/v1/customers")
public class CustomerApiController implements CustomerApiControllerDoc {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerAssembler customerAssembler;

    @Autowired
    private PagedResourcesAssembler<Customer> pagedResourcesAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Customer>> searchAll(Pageable pageable) {
        Page<Customer> customers = customerService.searchAll(pageable);

        return pagedResourcesAssembler.toModel(customers, customerAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<Customer> searchById(@PathVariable Long id) {
        Customer customer = customerService.searchById(id);

        return customerAssembler.toModel(customer);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<Customer> insert(@RequestBody @Valid CustomerDTO customerDTO) {
        Customer customer = customerService.insert(customerDTO);

        return customerAssembler.toModel(customer);
    }

    @PutMapping("/{id}")
    public EntityModel<Customer> update(
            @RequestBody @Valid CustomerDTO customerDTO,
            @PathVariable Long id) {

        Customer customer = customerService.update(customerDTO, id);

        return customerAssembler.toModel(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        customerService.deleteById(id);;

        return ResponseEntity.noContent().build();
    }
}
