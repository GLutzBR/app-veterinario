package br.com.lutztechnology.appveterinario.api.controller;

import br.com.lutztechnology.appveterinario.api.docs.AnimalApiControllerDoc;
import br.com.lutztechnology.appveterinario.api.dto.AnimalDTO;
import br.com.lutztechnology.appveterinario.api.hateoas.AnimalAssembler;
import br.com.lutztechnology.appveterinario.model.Animal;
import br.com.lutztechnology.appveterinario.services.AnimalService;
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
@RequestMapping("/api/v1/animals")
public class AnimalApiController implements AnimalApiControllerDoc {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private AnimalAssembler animalAssembler;

    @Autowired
    private PagedResourcesAssembler<Animal> pagedResourcesAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Animal>> searchAll(Pageable pageable) {
        Page<Animal> animals = animalService.searchAll(pageable);

        return pagedResourcesAssembler.toModel(animals, animalAssembler);
    }

    @GetMapping("/{id}")
    public EntityModel<Animal> searchById(@PathVariable Long id) {
        Animal animal = animalService.searchById(id);

        return animalAssembler.toModel(animal);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EntityModel<Animal> insert(@RequestBody @Valid AnimalDTO animalDTO) {
        Animal animal = animalService.insert(animalDTO);

        return animalAssembler.toModel(animal);
    }

    @PutMapping("/{id}")
    public EntityModel<Animal> update(
            @RequestBody @Valid AnimalDTO animalDTO,
            @PathVariable Long id) {
        Animal animal = animalService.update(animalDTO, id);

        return animalAssembler.toModel(animal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        animalService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
