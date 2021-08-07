package br.com.lutztechnology.appveterinario.web.controller;

import br.com.lutztechnology.appveterinario.dto.AlertDTO;
import br.com.lutztechnology.appveterinario.enums.State;
import br.com.lutztechnology.appveterinario.exceptions.AppRoleNotFoundException;
import br.com.lutztechnology.appveterinario.model.Employee;
import br.com.lutztechnology.appveterinario.services.EmployeeService;
import br.com.lutztechnology.appveterinario.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("admin/users/index");

        modelAndView.addObject("isAdmin", true);
        modelAndView.addObject("employees", employeeService.searchAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/users/details");

        modelAndView.addObject("isAdmin", true);
        modelAndView.addObject("employee", employeeService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/{id}/change-availability")
    public ModelAndView changeActive(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/users");

        employeeService.changeAvailability(id);

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("admin/users/form");

        modelAndView.addObject("isAdmin", true);
        modelAndView.addObject("employee", new Employee());
        modelAndView.addObject("roles", roleService.searchAll());
        modelAndView.addObject("states", State.values());

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @Valid Employee employee,
            BindingResult resultado,
            ModelMap model,
            RedirectAttributes attrs) {

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/users");

        if (resultado.hasErrors()) {
            model.addAttribute("roles", roleService.searchAll());

            modelAndView.setViewName("admin/users/form");

            return modelAndView;
        }

        try {
            employeeService.insert(employee);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Funcionário cadastrado com sucesso!",
                            "alert-success"));
        } catch (Exception e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Funcionário não pode ser cadastrado!",
                            "alert-danger"));
        }

        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/users/form");

        modelAndView.addObject("isAdmin", true);
        modelAndView.addObject("employee", employeeService.searchById(id));
        modelAndView.addObject("roles", roleService.searchAll());
        modelAndView.addObject("isVet", employeeService.searchById(id).getRole().getName().equals("VET"));
        modelAndView.addObject("states", State.values());

        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @Valid Employee employee,
            BindingResult resultado,
            @PathVariable Long id,
            ModelMap model,
            RedirectAttributes attrs) {

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/users");

        if (resultado.hasErrors()) {
            model.addAttribute("roles", roleService.searchAll());

            modelAndView.setViewName("admin/users/form");

            return modelAndView;
        }

        try {
            employeeService.update(employee, id);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Funcionário atualizado com sucesso!",
                            "alert-success"));
        } catch (AppRoleNotFoundException e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Funcionário não pode ser atualizado!",
                            "alert-danger"));
        }

        return modelAndView;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Employee> search(@RequestParam(name = "name", defaultValue = "") String search) {
        return employeeService.searchByNameOrEmail(search);
    }
}
