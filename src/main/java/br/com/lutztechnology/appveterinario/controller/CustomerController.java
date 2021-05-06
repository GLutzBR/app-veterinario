package br.com.lutztechnology.appveterinario.controller;

import br.com.lutztechnology.appveterinario.domain.model.Customer;
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
@RequestMapping("/app/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("/app/customers/index");
        Map<String, Object> newAttributes = new HashMap<>();
        List<Customer> customers = customerRepository.findAll();

        newAttributes.put("title", "Clientes");
        newAttributes.put("isCustomer", true);
        newAttributes.put("customers", customers);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(
            @PathVariable(name = "id") Long id,
            @RequestParam(name = "pets", defaultValue = "false", required = false) Boolean pets) {
        ModelAndView modelAndView = new ModelAndView("/app/customers/details");
        Map<String, Object> newAttributes = new HashMap<>();
        Customer customer = customerRepository.getOne(id);

        newAttributes.put("title", "Detalhes do cliente");
        newAttributes.put("isCustomer", true);
        newAttributes.put("customer", customer);
        newAttributes.put("pets", pets);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("/app/customers/insert");
        Map<String, Object> newAttributes = new HashMap<>();

        newAttributes.put("title", "Adicionar Novo Cliente");
        newAttributes.put("isCustomer", true);
        newAttributes.put("customer", new Customer());

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(@ModelAttribute("customer") Customer newCustomer,
                               @RequestParam(value = "action", required = true) String action,
                               final RedirectAttributes redirectAttributes) {
        ModelAndView modelAndView = new ModelAndView();

        switch (action) {
            case "save":
                modelAndView.setViewName("redirect:/app/customers");
                break;
            case "saveAndAddPet":
                redirectAttributes.addFlashAttribute("customer", newCustomer);
                modelAndView.setViewName("redirect:/app/animals/insert-with-owner");
                break;
        }

        customerRepository.save(newCustomer);

        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(
            @PathVariable(name = "id") Long id) {

        ModelAndView modelAndView = new ModelAndView("/app/customers/update");
        Map<String, Object> newAttributes = new HashMap<>();
        Customer customerToUpdate = customerRepository.getOne(id);

        newAttributes.put("title", "Editar o Cliente");
        newAttributes.put("isCustomer", true);
        newAttributes.put("customer", customerToUpdate);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @ModelAttribute("customer") Customer customerToUpdate,
            @RequestParam(value = "action", required = true) String action,
            final RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView();

        switch (action) {
            case "save":
                modelAndView.setViewName("redirect:/app/customers");
                break;
            case "saveAndUpdatePet":
                redirectAttributes.addFlashAttribute("customer", customerToUpdate);
                if (customerToUpdate.getPets().isEmpty()) {
                    modelAndView.setViewName("redirect:/app/animals/insert-with-owner");
                } else {
                    modelAndView.setViewName("redirect:/app/animals/update-with-owner");
                }
                break;
        }

        customerRepository.save(customerToUpdate);

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView delete(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/customers");

        customerRepository.deleteById(id);

        return modelAndView;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Customer> search(@RequestParam(name = "name", defaultValue = "") String customerName) {
        return customerRepository.findByFullName(customerName);
    }
}
