package br.com.lutztechnology.appveterinario.domain.model.dto;

import br.com.lutztechnology.appveterinario.domain.model.Animal;

import java.util.List;

public class AnimalUpdateDto {

    private List<Animal> animals;

    public AnimalUpdateDto() {
    }

    public AnimalUpdateDto(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public void addAnimal(Animal animal) {
        this.animals.add(animal);
    }
}
