package br.com.lutztechnology.appveterinario.services;

import br.com.lutztechnology.appveterinario.dto.AnimalUpdateDTO;
import br.com.lutztechnology.appveterinario.exceptions.AnimalHasMedicalRecordsException;
import br.com.lutztechnology.appveterinario.exceptions.AnimalNotFoundException;
import br.com.lutztechnology.appveterinario.model.Animal;
import br.com.lutztechnology.appveterinario.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    public List<Animal> searchAll() {
        return animalRepository.findAll();
    }

    public Page<Animal> searchAll(Pageable pageable) {
        return animalRepository.findAll(pageable);
    }

    public Animal searchById(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new AnimalNotFoundException(id));
    }

    public List<Animal> searchByOwnerId(Long id) {
        return animalRepository.findByOwnerId(id);
    }

    public List<Animal> searchByName(String name) {
        return animalRepository.findByName(name);
    }

    public Animal insert(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal update(Animal animal, Long id) {
        searchById(id);

        return animalRepository.save(animal);
    }

    public void updateAll(AnimalUpdateDTO animals) {
        List<Animal> animalList = new ArrayList<>();
        for (Animal animal : animals.getAnimals()) {
            // o método searchById serve para validar se o animal repassado pelo formulário existe no banco
            searchById(animal.getId());
            animalList.add(animal);
        }

        animalRepository.saveAll(animalList);
    }

    public void deleteById(Long id) {
        Animal animal = searchById(id);
        if (animal.getMedicalRecords().isEmpty()) {
            animalRepository.deleteById(id);
        } else {
            throw new AnimalHasMedicalRecordsException(id);
        }
    }
}
