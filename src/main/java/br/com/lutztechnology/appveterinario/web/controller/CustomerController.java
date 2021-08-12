package br.com.lutztechnology.appveterinario.web.controller;

import br.com.lutztechnology.appveterinario.dto.AlertDTO;
import br.com.lutztechnology.appveterinario.enums.State;
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

        modelAndView.addObject("isCustomer", true);
        modelAndView.addObject("customers", customerService.searchAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("app/customers/details");

        modelAndView.addObject("isCustomer", true);
        modelAndView.addObject("customer", customerService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("app/customers/form");

        modelAndView.addObject("isCustomer", true);
        modelAndView.addObject("customer", new Customer());
        modelAndView.addObject("states", State.values());

        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("app/customers/form");

        modelAndView.addObject("isCustomer", true);
        modelAndView.addObject("customer", customerService.searchById(id));
        modelAndView.addObject("states", State.values());

        return modelAndView;
    }

    @PostMapping({"/insert", "/{id}/update"})
    public ModelAndView save(
            @Valid Customer customer,
            BindingResult result,
            @PathVariable(required = false) Long id,
            @RequestParam(value = "action") String action,
            final RedirectAttributes attrs) {
        ModelAndView modelAndView = new ModelAndView("redirect:/app/customers");

        if (result.hasErrors()) {
            modelAndView.setViewName("app/customers/form");
            return modelAndView;
        }

        if (action.equals("saveAndAddPet")) {
            attrs.addFlashAttribute("customer", customer);
            modelAndView.setViewName("redirect:/app/animals/insert");
        }

        if (customer.getId() == null) {
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
        } else {
            try {
                customerService.update(customer, id);
                attrs.addFlashAttribute(
                        "alert",
                        new AlertDTO(
                                "Cliente atualizado com sucesso!",
                                "alert-success"));
            } catch (CustomerNotFoundException e) {
                attrs.addFlashAttribute(
                        "alert",
                        new AlertDTO(
                                "Cliente não pode ser atualizado!",
                                "alert-danger"));
                modelAndView.setViewName("redirect:/app/customers");
            }
        }

        return modelAndView;
    }

    @GetMapping("/{id}/update-pet")
    public String updatePet(@PathVariable Long id, RedirectAttributes attrs) {
        attrs.addFlashAttribute("customer", customerService.searchById(id));
        return "redirect:/app/animals/update-with-owner";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            customerService.deleteById(id);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cliente excluído com sucesso!",
                            "alert-success"));
        } catch (CustomerNotFoundException e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cliente não pode ser excluído!",
                            "alert-danger"));
        }

        return "redirect:/app/customers";
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Customer> search(@RequestParam(name = "name", defaultValue = "") String name) {
        return customerService.searchByName(name);
    }
}
