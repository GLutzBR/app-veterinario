package br.com.lutztechnology.appveterinario.web.controller;

import br.com.lutztechnology.appveterinario.dto.AlertDTO;
import br.com.lutztechnology.appveterinario.model.Role;
import br.com.lutztechnology.appveterinario.exceptions.AppRoleNotFoundException;
import br.com.lutztechnology.appveterinario.exceptions.RoleHasEmployeesException;
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
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("admin/roles/index");

        modelAndView.addObject("isAdmin", true);
        modelAndView.addObject("roles", roleService.searchAll());

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("admin/roles/details");

        modelAndView.addObject("isAdmin", true);
        modelAndView.addObject("role", roleService.searchById(id));

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("admin/roles/form");

        modelAndView.addObject("isAdmin", true);
        modelAndView.addObject("role", new Role());

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @Valid Role role,
            BindingResult result,
            RedirectAttributes attrs) {

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/roles");

        if (result.hasErrors()) {
            modelAndView.setViewName("admin/roles/form");
            return modelAndView;
        }

        try {
            roleService.insert(role);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo cadastrado com sucesso!",
                            "alert-success"));
        } catch (Exception e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo não pode ser cadastrado!",
                            "alert-danger"));
        }

        return modelAndView;
    }
    
    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/roles/form");
        
        modelAndView.addObject("isAdmin", true);
        modelAndView.addObject("role", roleService.searchById(id));
        
        return modelAndView;
    }
    
    @PostMapping("/{id}/update")
    public ModelAndView update(
            @Valid Role role,
            BindingResult result,
            @PathVariable Long id,
            RedirectAttributes attrs) {

        ModelAndView modelAndView = new ModelAndView("redirect:/admin/roles");

        if (result.hasErrors()) {
            modelAndView.setViewName("admin/roles/form");

            return modelAndView;
        }

        try {
            roleService.update(role, id);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo atualizado com sucesso!",
                            "alert-success"));
        } catch (AppRoleNotFoundException e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo não pode ser atualizado!",
                            "alert-danger"));
        }

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            roleService.deleteById(id);
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo excluído com sucesso!",
                            "alert-success"));
        } catch (RoleHasEmployeesException e) {
            attrs.addFlashAttribute(
                    "alert",
                    new AlertDTO(
                            "Cargo não pode ser excluído!",
                            "alert-danger"));
        }

        return "redirect:/admin/roles";
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody List<Role> search(@RequestParam(name = "name", defaultValue = "") String roleName) {
        return roleService.searchByName(roleName);
    }
}
