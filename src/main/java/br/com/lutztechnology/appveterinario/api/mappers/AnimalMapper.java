package br.com.lutztechnology.appveterinario.api.mappers;

import br.com.lutztechnology.appveterinario.api.dto.AnimalDTO;
import br.com.lutztechnology.appveterinario.model.Animal;
import br.com.lutztechnology.appveterinario.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnimalMapper {

    @Autowired
    private CustomerService customerService;

    public Animal convertToEntity(AnimalDTO animalDTO){
        Animal animal = new Animal();

        animal.setName(animalDTO.getName());
        animal.setAge(animalDTO.getAge());
        animal.setBreed(animalDTO.getBreed());
        animal.setOwner(customerService.searchById(animalDTO.getOwnerId()));

        return animal;
    }
}
