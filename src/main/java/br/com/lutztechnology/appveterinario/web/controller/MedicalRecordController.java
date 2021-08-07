package br.com.lutztechnology.appveterinario.web.controller;

import br.com.lutztechnology.appveterinario.dto.AlertDTO;
import br.com.lutztechnology.appveterinario.exceptions.MedicalRecordNotFoundException;
import br.com.lutztechnology.appveterinario.model.Animal;
import br.com.lutztechnology.appveterinario.model.AppUserDetailsImpl;
import br.com.lutztechnology.appveterinario.model.Customer;
import br.com.lutztechnology.appveterinario.model.MedicalRecord;
import br.com.lutztechnology.appveterinario.repository.AnimalRepository;
import br.com.lutztechnology.appveterinario.repository.CustomerRepository;
import br.com.lutztechnology.appveterinario.repository.MedicalRecordRepository;
import br.com.lutztechnology.appveterinario.services.EmployeeService;
import br.com.lutztechnology.appveterinario.services.MedicalRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/app/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private MedicalRecordService medicalRecordService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("app/medical-records/index");

        modelAndView.addObject("title", "Prontuários");
        modelAndView.addObject("isAtendimento", true);
        modelAndView.addObject("medicalRecords", medicalRecordService.searchAll());

        return modelAndView;
    }

    @GetMapping("/archived")
    public ModelAndView archived() {
        ModelAndView modelAndView = new ModelAndView("app/medical-records/archived");

        modelAndView.addObject("title", "Prontuários Arquivados");
        modelAndView.addObject("isAtendimento", true);
        modelAndView.addObject("medicalRecords", medicalRecordService.searchArchived());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("app/medical-records/details");

        modelAndView.addObject("title", "Detalhes do Prontuário");
        modelAndView.addObject("isAtendimento", true);
        modelAndView.addObject("medicalRecord", medicalRecordService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("app/medical-records/insert");

        modelAndView.addObject("title", "Registro de Prontuário");
        modelAndView.addObject("isAtendimento", true);
        modelAndView.addObject("medicalRecord", new MedicalRecord());
        fillForm(modelAndView);

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @Valid MedicalRecord medicalRecord,
            BindingResult result,
            RedirectAttributes attrs,
            @AuthenticationPrincipal AppUserDetailsImpl appUserDetails) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/medical-records");

        if (result.hasErrors()) {
            modelAndView.setViewName("app/medical-records/insert");
            fillForm(modelAndView);
            return modelAndView;
        }
        try {
            medicalRecordService.insert(medicalRecord, employeeService.searchById(appUserDetails.getId()));
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
                            "alert-success"));
        }

        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("app/medical-records/update");

        modelAndView.addObject("title", "Alteração do Registro de Prontuário");
        modelAndView.addObject("isAtendimento", true);
        modelAndView.addObject("medicalRecord", medicalRecordService.searchById(id));
        fillForm(modelAndView);

        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @Valid MedicalRecord medicalRecord,
            BindingResult result,
            RedirectAttributes attrs,
            @PathVariable Long id,
            @AuthenticationPrincipal AppUserDetailsImpl appUserDetails) {

        ModelAndView modelAndView = new ModelAndView("redirect:/app/medical-records");

        if (result.hasErrors()) {
            modelAndView.setViewName("app/medical-records/update");
            fillForm(modelAndView);
            return modelAndView;
        }

        try {
            medicalRecordService.update(medicalRecord, id, employeeService.searchById(appUserDetails.getId()));
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Prontuário atualizado com sucesso!",
                            "alert-success"));
        } catch (MedicalRecordNotFoundException e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Prontuário não pode ser atualizado!",
                            "alert-danger"));
        }

        return modelAndView;
    }

    // TODO: mudar para POST
    @GetMapping("/{id}/archive")
    public ModelAndView archive(@PathVariable Long id, RedirectAttributes attrs) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/medical-records");

        try {
            medicalRecordService.archive(id);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Prontuário arquivado com sucesso!",
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
        modelAndView.addObject("customers", customerRepository.findAll());
        modelAndView.addObject("animals", animalRepository.findAll());
    }

    // TODO: mover buscas para seção API
    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<MedicalRecord> search(@RequestParam(name = "patient") String patient) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecordRepository.findByAnimalName(patient)) {
            if (!medicalRecord.getArchived()) {
                medicalRecords.add(medicalRecord);
            }
        }
        return medicalRecords;
    }

    @GetMapping(value = "/searchArchived", produces = "application/json")
    public @ResponseBody
    List<MedicalRecord> searchArchived(@RequestParam(name = "patient") String patient) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecordRepository.findByAnimalName(patient)) {
            if (medicalRecord.getArchived()) {
                medicalRecords.add(medicalRecord);
            }
        }
        return medicalRecords;
    }

    @GetMapping(value = "/searchAnimals", produces = "application/json")
    public @ResponseBody
    List<Animal> searchAnimals(@RequestParam(name = "ownerId", defaultValue = "") String id) {
        return animalRepository.findByOwnerId(Long.parseLong(id));
    }

    @GetMapping(value = "/searchOwner", produces = "application/json")
    public @ResponseBody
    Customer searchOwner(@RequestParam(name = "petId", defaultValue = "") String id) {
        Animal animal = animalRepository.getOne(Long.parseLong(id));
        return customerRepository.getOne(animal.getOwner().getId());
    }
}
