package br.com.lutztechnology.appveterinario.web.controller;

import br.com.lutztechnology.appveterinario.dto.AlertDTO;
import br.com.lutztechnology.appveterinario.exceptions.CustomerNotFoundException;
import br.com.lutztechnology.appveterinario.model.Customer;
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
@RequestMapping("/app/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("app/customers/index");

        modelAndView.addObject("title", "Listagem de clientes");
        modelAndView.addObject("isCustomer", true);
        modelAndView.addObject("customers", customerService.searchAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(
            @PathVariable Long id,
            @RequestParam(name = "pets", defaultValue = "false", required = false) Boolean pets) {
        ModelAndView modelAndView = new ModelAndView("app/customers/details");

        modelAndView.addObject("title", "Detalhes do cliente");
        modelAndView.addObject("isCustomer", true);
        modelAndView.addObject("customer", customerService.searchById(id));
        modelAndView.addObject("pets", pets);

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("app/customers/insert");

        modelAndView.addObject("title", "Adicionar Novo Cliente");
        modelAndView.addObject("isCustomer", true);
        modelAndView.addObject("customer", new Customer());

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @Valid Customer customer,
            BindingResult result,
            @RequestParam(value = "action") String action,
            final RedirectAttributes attrs) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/customers");

        if (result.hasErrors()) {
            modelAndView.setViewName("app/customers/insert");
            return modelAndView;
        }

        if (action.equals("saveAndAddPet")) {
            attrs.addFlashAttribute("customer", customer);
            modelAndView.setViewName("redirect:/app/animals/insert-with-owner");
        }

        try {
            customerService.insert(customer);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cliente cadastrado com sucesso!",
                            "alert-success"));
        } catch (Exception e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cliente não pode ser cadastrado!",
                            "alert-danger"));
            modelAndView.setViewName("redirect:/app/customers");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("app/customers/update");

        modelAndView.addObject("title", "Editar o Cliente");
        modelAndView.addObject("isCustomer", true);
        modelAndView.addObject("customer", customerService.searchById(id));

        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @Valid Customer customer,
            BindingResult result,
            @PathVariable Long id,
            @RequestParam(value = "action") String action,
            final RedirectAttributes attrs) {

        ModelAndView modelAndView = new ModelAndView("redirect:/app/customers");

        if (result.hasErrors()) {
            modelAndView.setViewName("app/customers/update");
            return modelAndView;
        }

        if (action.equals("saveAndUpdatePet")) {
            attrs.addFlashAttribute("customer", customer);
            if (customer.getPets().isEmpty()) {
                modelAndView.setViewName("redirect:/app/animals/insert-with-owner");
            } else {
                modelAndView.setViewName("redirect:/app/animals/update-with-owner");
            }
        }

        try {
            customerService.update(customer, id);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cliente atualizado com sucesso!",
                            "alert-danger"));
        } catch (CustomerNotFoundException e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cliente não pode ser atualizado!",
                            "alert-danger"));
            modelAndView.setViewName("redirect:/app/customers");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        customerService.deleteById(id);

        return "redirect:/app/customers";
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Customer> search(@RequestParam(name = "name", defaultValue = "") String name) {
        return customerService.searchByName(name);
    }
}
