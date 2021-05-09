package br.com.lutztechnology.appveterinario.controller;

import br.com.lutztechnology.appveterinario.domain.model.Role;
import br.com.lutztechnology.appveterinario.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/roles")
public class RoleController {

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("admin/roles/index");
        Map<String, Object> newAttributes = new HashMap<>();
        List<Role> roles = roleRepository.findAll();

        newAttributes.put("title", "Níveis de Acesso");
        newAttributes.put("isAdmin", true);
        newAttributes.put("roles", roles);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable(name = "id") Long id){
        ModelAndView modelAndView = new ModelAndView("admin/roles/details");
        Role role = roleRepository.getOne(id);
        Map<String, Object> newAttributes = new HashMap<>();

        newAttributes.put("title", "Detalhes do nível de acesso");
        newAttributes.put("isAdmin", true);
        newAttributes.put("role", role);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody List<Role> search(@RequestParam(name = "name", defaultValue = "") String roleName) {
        return roleRepository.findByNameOrType(roleName);
    }
}
