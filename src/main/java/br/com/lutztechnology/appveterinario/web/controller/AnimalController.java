package br.com.lutztechnology.appveterinario.web.controller;

import br.com.lutztechnology.appveterinario.dto.AlertDTO;
import br.com.lutztechnology.appveterinario.dto.AnimalUpdateDTO;
import br.com.lutztechnology.appveterinario.exceptions.AnimalNotFoundException;
import br.com.lutztechnology.appveterinario.model.Animal;
import br.com.lutztechnology.appveterinario.model.Customer;
import br.com.lutztechnology.appveterinario.services.AnimalService;
import br.com.lutztechnology.appveterinario.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/app/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("app/animals/index");

        modelAndView.addObject("isAnimal", true);
        modelAndView.addObject("animals", animalService.searchAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("app/animals/details");

        modelAndView.addObject("isAnimal", true);
        modelAndView.addObject("animal", animalService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("app/animals/form");

        modelAndView.addObject("isAnimal", true);
        modelAndView.addObject("animal", new Animal());
        modelAndView.addObject("customers", customerService.searchAll());

        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("app/animals/form");

        modelAndView.addObject("isAnimal", true);
        modelAndView.addObject("animal", animalService.searchById(id));
        modelAndView.addObject("customers", customerService.searchAll());

        return modelAndView;
    }

    @PostMapping({"/insert", "/{id}/update"})
    public ModelAndView save(
            @Valid Animal animal,
            BindingResult result,
            @PathVariable(required = false) Long id,
            @RequestParam(value = "action") String action,
            final RedirectAttributes attrs){
        ModelAndView modelAndView = new ModelAndView("redirect:/app/animals");

        if (result.hasErrors()) {
            modelAndView.setViewName("app/animals/form");
            return modelAndView;
        }

        if (action.equals("saveAndAddPet")) {
            attrs.addFlashAttribute("customer", animal.getOwner());
            modelAndView.setViewName("redirect:/app/animals/insert");
        }

        if (animal.getId() == null) {
            try {
                animalService.insert(animal);
                attrs.addFlashAttribute(
                        "alert",
                        new AlertDTO(
                                "Pet cadastrado com sucesso!",
                                "alert-success"));
            } catch (Exception e) {
                attrs.addFlashAttribute(
                        "alert",
                        new AlertDTO(
                                "Pet não pode ser cadastrado!",
                                "alert-danger"));
                modelAndView.setViewName("redirect:/app/animals");
            }
        } else {
            try {
                animalService.update(animal, id);
                attrs.addFlashAttribute(
                        "alert",
                        new AlertDTO(
                                "Pet atualizado com sucesso!",
                                "alert-success"));
            } catch (AnimalNotFoundException e) {
                attrs.addFlashAttribute(
                        "alert",
                        new AlertDTO(
                                "Pet não pode ser atualizado!",
                                "alert-danger"));
                modelAndView.setViewName("redirect:/app/animals");
            }
        }

        return modelAndView;
    }

    @GetMapping("/update-with-owner")
    public ModelAndView update(@ModelAttribute(value = "customer") Customer owner) {
        ModelAndView modelAndView = new ModelAndView("app/animals/form-list-animals");
        AnimalUpdateDTO animalUpdateDTO = new AnimalUpdateDTO(animalService.searchByOwnerId(owner.getId()));

        modelAndView.addObject("isAnimal", true);
        modelAndView.addObject("form", animalUpdateDTO);
        modelAndView.addObject("customers", customerService.searchAll());

        return modelAndView;
    }

    @PostMapping("/update")
    public ModelAndView update(
            @ModelAttribute(name = "animals") AnimalUpdateDTO animals,
            BindingResult result,
            RedirectAttributes attrs) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/animals");

        if (result.hasErrors()) {
            modelAndView.setViewName("app/animals/form-list-animals");
            return modelAndView;
        }

        try {
            animalService.updateAll(animals);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Pets atualizados com sucesso!",
                            "alert-success"));
        } catch (AnimalNotFoundException e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Pets não puderam ser atualizados!",
                            "alert-danger"));
            modelAndView.setViewName("redirect:/app/animals");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id, RedirectAttributes attrs) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/animals");

        try {
            animalService.deleteById(id);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Pet excluído com sucesso!",
                            "alert-success"));
        } catch (Exception e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Pet não pode ser excluído!",
                            "alert-danger"));
            modelAndView.setViewName("redirect:/app/customers");
        }

        return modelAndView;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Animal> search(@RequestParam(name = "name", defaultValue = "") String animalName) {
        return animalService.searchByName(animalName);
    }
}
