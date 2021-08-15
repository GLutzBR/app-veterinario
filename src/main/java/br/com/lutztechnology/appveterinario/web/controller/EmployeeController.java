package br.com.lutztechnology.appveterinario.web.controller;

import br.com.lutztechnology.appveterinario.dto.AlertDTO;
import br.com.lutztechnology.appveterinario.enums.State;
import br.com.lutztechnology.appveterinario.exceptions.AppRoleNotFoundException;
import br.com.lutztechnology.appveterinario.exceptions.EmployeeNotFoundException;
import br.com.lutztechnology.appveterinario.model.Employee;
import br.com.lutztechnology.appveterinario.services.EmployeeService;
import br.com.lutztechnology.appveterinario.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public ModelAndView changeActive(@PathVariable Long id, RedirectAttributes attrs) {
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/users");

        try {
            Employee employee = employeeService.changeAvailability(id);
            String message = employee.getActive() ? "Funcionário habilitado com sucesso!" : "Funcionário desabilitado com sucesso!";
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            message,
                            "alert-success"));
        } catch (EmployeeNotFoundException e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Funcionário não pode ser alterado!",
                            "alert-danger"));
        }

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("admin/users/form");

        modelAndView.addObject("employee", new Employee());
        fillForm(modelAndView);

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @Valid Employee employee,
            BindingResult resultado,
            RedirectAttributes attrs) {

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/users");

        if (resultado.hasErrors()) {
            fillForm(modelAndView);

            modelAndView.setViewName("admin/users/form");
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Funcionário não pode ser cadastrado!",
                            "alert-danger"));

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

        modelAndView.addObject("employee", employeeService.searchById(id));
        modelAndView.addObject("isVet", employeeService.searchById(id).getRole().getName().equals("VET"));
        fillForm(modelAndView);

        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @Valid Employee employee,
            BindingResult resultado,
            @PathVariable Long id,
            RedirectAttributes attrs) {

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/users");

        if (resultado.hasErrors()) {
            fillForm(modelAndView);

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
    private void fillForm(ModelAndView modelAndView) {
        modelAndView.addObject("isAdmin", true);
        modelAndView.addObject("roles", roleService.searchAll());
        modelAndView.addObject("states", State.values());
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<Employee> search(@RequestParam(name = "name", defaultValue = "") String search) {
        return employeeService.searchByNameOrEmail(search);
    }
}
