package br.com.lutztechnology.appveterinario.controller;

import br.com.lutztechnology.appveterinario.domain.model.Animal;
import br.com.lutztechnology.appveterinario.domain.model.Customer;
import br.com.lutztechnology.appveterinario.domain.model.MedicalRecord;
import br.com.lutztechnology.appveterinario.domain.model.User;
import br.com.lutztechnology.appveterinario.domain.repository.AnimalRepository;
import br.com.lutztechnology.appveterinario.domain.repository.CustomerRepository;
import br.com.lutztechnology.appveterinario.domain.repository.MedicalRecordRepository;
import br.com.lutztechnology.appveterinario.domain.repository.UserRepository;
import br.com.lutztechnology.appveterinario.domain.service.AppUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/app/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/app/medical-records/index");
        Map<String, Object> newAttributes = new HashMap<>();
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        for (MedicalRecord medicalRecord : medicalRecordRepository.findAll()) {
            if (!medicalRecord.getArchived()) {
                medicalRecords.add(medicalRecord);
            }
        }

        newAttributes.put("title", "Prontuários");
        newAttributes.put("isAtendimento", true);
        newAttributes.put("medicalRecords", medicalRecords);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/archived")
    public ModelAndView archived() {
        ModelAndView modelAndView = new ModelAndView("/app/medical-records/archived");
        Map<String, Object> newAttributes = new HashMap<>();
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        for (MedicalRecord medicalRecord : medicalRecordRepository.findAll()) {
            if (medicalRecord.getArchived()) {
                medicalRecords.add(medicalRecord);
            }
        }

        newAttributes.put("title", "Prontuários Arquivados");
        newAttributes.put("isAtendimento", true);
        newAttributes.put("medicalRecords", medicalRecords);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/app/medical-records/details");
        Map<String, Object> newAttributes = new HashMap<>();
        MedicalRecord medicalRecord = medicalRecordRepository.getOne(id);

        newAttributes.put("title", "Detalhes do Prontuário");
        newAttributes.put("isAtendimento", true);
        newAttributes.put("medicalRecord", medicalRecord);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("/app/medical-records/insert");
        Map<String, Object> newAttributes = new HashMap<>();
        List<Customer> customers = customerRepository.findAll();
        List<Animal> animals = animalRepository.findAll();

        newAttributes.put("title", "Registro de Prontuário");
        newAttributes.put("isAtendimento", true);
        newAttributes.put("medicalRecord", new MedicalRecord());
        newAttributes.put("customers", customers);
        newAttributes.put("animals", animals);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @ModelAttribute(name = "medicalRecord") MedicalRecord medicalRecord,
            @AuthenticationPrincipal AppUserDetailsImpl appUserDetails) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/medical-records");
        User user = userRepository.getOne(appUserDetails.getId());

        medicalRecord.setUser(user);

        medicalRecordRepository.save(medicalRecord);

        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("/app/medical-records/update");
        Map<String, Object> newAttributes = new HashMap<>();
        MedicalRecord medicalRecord = medicalRecordRepository.getOne(id);
        List<Customer> customers = customerRepository.findAll();
        List<Animal> animals = animalRepository.findAll();

        newAttributes.put("title", "Alteração do Registro de Prontuário");
        newAttributes.put("isAtendimento", true);
        newAttributes.put("medicalRecord", medicalRecord);
        newAttributes.put("customers", customers);
        newAttributes.put("animals", animals);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @ModelAttribute("medicalRecord") MedicalRecord medicalRecord,
            @AuthenticationPrincipal AppUserDetailsImpl appUserDetails) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/medical-records");
        User user = userRepository.getOne(appUserDetails.getId());

        medicalRecord.setUser(user);

        medicalRecordRepository.save(medicalRecord);

        return modelAndView;
    }

    @GetMapping("/{id}/archive")
    public ModelAndView archive(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/medical-records");
        MedicalRecord medicalRecord = medicalRecordRepository.getOne(id);

        medicalRecord.setArchived(!medicalRecord.getArchived());

        medicalRecordRepository.save(medicalRecord);

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/medical-records");

        medicalRecordRepository.deleteById(id);

        return modelAndView;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<MedicalRecord> search(@RequestParam(name = "patient") String patient) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecordRepository.findByPetName(patient)) {
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
        for (MedicalRecord medicalRecord : medicalRecordRepository.findByPetName(patient)) {
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
