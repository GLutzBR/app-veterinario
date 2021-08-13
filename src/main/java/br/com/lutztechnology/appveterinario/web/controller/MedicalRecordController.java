package br.com.lutztechnology.appveterinario.web.controller;

import br.com.lutztechnology.appveterinario.dto.AlertDTO;
import br.com.lutztechnology.appveterinario.exceptions.MedicalRecordNotFoundException;
import br.com.lutztechnology.appveterinario.model.Animal;
import br.com.lutztechnology.appveterinario.model.Customer;
import br.com.lutztechnology.appveterinario.model.MedicalRecord;
import br.com.lutztechnology.appveterinario.services.AnimalService;
import br.com.lutztechnology.appveterinario.services.CustomerService;
import br.com.lutztechnology.appveterinario.services.EmployeeService;
import br.com.lutztechnology.appveterinario.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/app/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ModelAndView index(
            @RequestParam(required = false, name = "archived", defaultValue = "false") Boolean archived) {
        ModelAndView modelAndView = new ModelAndView("app/medical-records/index");

        modelAndView.addObject("isAtendimento", true);
        if (archived) {
            modelAndView.addObject("medicalRecords", medicalRecordService.searchArchived());
        } else {
            modelAndView.addObject("medicalRecords", medicalRecordService.searchAll());
        }

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("app/medical-records/details");

        modelAndView.addObject("isAtendimento", true);
        modelAndView.addObject("medicalRecord", medicalRecordService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert(Principal principal) {
        ModelAndView modelAndView = new ModelAndView("app/medical-records/form");

        modelAndView.addObject("isAtendimento", true);
        modelAndView.addObject("medicalRecord", new MedicalRecord());
        modelAndView.addObject("veterinarian", employeeService.searchByEmail(principal.getName()).getId());
        fillForm(modelAndView);

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @Valid MedicalRecord medicalRecord,
            BindingResult result,
            RedirectAttributes attrs,
            Principal principal) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/medical-records");

        if (result.hasErrors()) {
            modelAndView.setViewName("app/medical-records/form");
            modelAndView.addObject("veterinarian", employeeService.searchByEmail(principal.getName()));
            fillForm(modelAndView);
            return modelAndView;
        }

        try {
            medicalRecordService.insert(medicalRecord);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Prontuário cadastrado com sucesso!",
                            "alert-success"));
        } catch (Exception e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Prontuário não pode ser cadastrado!",
                            "alert-danger"));
        }

        return modelAndView;
    }

    @GetMapping("/{id}/archive")
    public ModelAndView archive(@PathVariable Long id, RedirectAttributes attrs) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/medical-records");

        try {
            MedicalRecord medicalRecord = medicalRecordService.archive(id);
            String message;
            if (medicalRecord.getArchived()) {
                message = "Prontuário arquivado com sucesso!";
            } else {
                message = "Prontuário desarquivado com sucesso!";
                modelAndView.setViewName("redirect:/app/medical-records?archived=true");
            }
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            message,
                            "alert-success"));
        } catch (MedicalRecordNotFoundException e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Prontuário não pode ser arquivado!",
                            "alert-danger"));
        }

        return modelAndView;
    }

    private void fillForm(ModelAndView modelAndView) {
        modelAndView.addObject("customers", customerService.searchAll());
        modelAndView.addObject("animals", animalService.searchAll());
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<MedicalRecord> search(@RequestParam(name = "patient", defaultValue = "") String patient) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecordService.searchByAnimalName(patient)) {
            if (!medicalRecord.getArchived()) {
                medicalRecords.add(medicalRecord);
            }
        }
        return medicalRecords;
    }

    @GetMapping(value = "/searchArchived", produces = "application/json")
    public @ResponseBody
    List<MedicalRecord> searchArchived(@RequestParam(name = "patient", defaultValue = "") String patient) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecordService.searchByAnimalName(patient)) {
            if (medicalRecord.getArchived()) {
                medicalRecords.add(medicalRecord);
            }
        }
        return medicalRecords;
    }

    @GetMapping(value = "/searchAnimals", produces = "application/json")
    public @ResponseBody
    List<Animal> searchAnimals(@RequestParam(name = "ownerId", defaultValue = "") String id) {
        return animalService.searchByOwnerId(Long.parseLong(id));
    }

    @GetMapping(value = "/searchOwner", produces = "application/json")
    public @ResponseBody
    Customer searchOwner(@RequestParam(name = "petId", defaultValue = "") String id) {
        Animal animal = animalService.searchById(Long.parseLong(id));
        return customerService.searchById(animal.getOwner().getId());
    }
}
