package br.com.lutztechnology.appveterinario.controller;

import br.com.lutztechnology.appveterinario.domain.model.Animal;
import br.com.lutztechnology.appveterinario.domain.model.Customer;
import br.com.lutztechnology.appveterinario.domain.model.dto.AnimalUpdateDto;
import br.com.lutztechnology.appveterinario.domain.repository.AnimalRepository;
import br.com.lutztechnology.appveterinario.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/animals")
public class AnimalController {

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("app/animals/index");
        List<Animal> animals = animalRepository.findAll();
        Map<String, Object> newAttributes = new HashMap<>();

        newAttributes.put("title", "Pets");
        newAttributes.put("isAnimal", true);
        newAttributes.put("animals", animals);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("app/animals/details");
        Map<String, Object> newAttributes = new HashMap<>();
        Animal animal = animalRepository.getOne(id);

        newAttributes.put("title", "Detalhes do Pet");
        newAttributes.put("isAnimal", true);
        newAttributes.put("animal", animal);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("app/animals/insert");
        Map<String, Object> newAttributes = new HashMap<>();
        List<Customer> customers = customerRepository.findAll();

        newAttributes.put("title", "Adicionar novo Pet");
        newAttributes.put("isAnimal", true);
        newAttributes.put("animal", new Animal());
        newAttributes.put("customers", customers);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/insert-with-owner")
    public ModelAndView insert(@ModelAttribute(value = "customer") Customer owner) {
        ModelAndView modelAndView = new ModelAndView("app/animals/insert-with-owner");
        Map<String, Object> newAttributes = new HashMap<>();

        newAttributes.put("title", "Adicionar novo Pet");
        newAttributes.put("isAnimal", true);
        newAttributes.put("animal", new Animal());
        newAttributes.put("owner", owner);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }


    @PostMapping("/insert")
    public ModelAndView insert(@ModelAttribute("animal") Animal newAnimal,
                               @RequestParam(value = "action", required = true) String action,
                               final RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        switch (action) {
            case "save":
                modelAndView.setViewName("redirect:/app/animals");
                break;
            case "saveAndAddPet":
                redirectAttributes.addFlashAttribute("customer", newAnimal.getOwner());
                modelAndView.setViewName("redirect:/app/animals/insert-with-owner");
                break;
        }

        animalRepository.save(newAnimal);

        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("app/animals/update");
        Map<String, Object> newAttributes = new HashMap<>();
        Animal animalToUpdate = animalRepository.getOne(id);
        List<Customer> customers = customerRepository.findAll();

        newAttributes.put("title", "Editar Pet");
        newAttributes.put("isAnimal", true);
        newAttributes.put("animal", animalToUpdate);
        newAttributes.put("customers", customers);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(@ModelAttribute("animal") Animal animalToUpdate) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/animals");

        animalRepository.save(animalToUpdate);

        return modelAndView;
    }

    @GetMapping("/update-with-owner")
    public ModelAndView update(@ModelAttribute(value = "customer") Customer owner) {
        ModelAndView modelAndView = new ModelAndView("app/animals/update-with-owner");
        Map<String, Object> newAttributes = new HashMap<>();
        AnimalUpdateDto animalsToUpdate = new AnimalUpdateDto(animalRepository.findByOwnerId(owner.getId()));
        List<Customer> customers = customerRepository.findAll();

        newAttributes.put("title", "Editar Pets");
        newAttributes.put("isAnimal", true);
        newAttributes.put("form", animalsToUpdate);
        newAttributes.put("customers", customers);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(@ModelAttribute(value = "animals") AnimalUpdateDto animalsToUpdate) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/animals");

        animalRepository.saveAll(animalsToUpdate.getAnimals());

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/animals");

        animalRepository.deleteById(id);

        return modelAndView;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Animal> search(@RequestParam(name = "name", defaultValue = "") String animalName) {
        return animalRepository.findByName(animalName);
    }
}
