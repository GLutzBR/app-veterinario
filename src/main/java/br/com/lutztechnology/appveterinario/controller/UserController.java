package br.com.lutztechnology.appveterinario.controller;

import br.com.lutztechnology.appveterinario.domain.model.Role;
import br.com.lutztechnology.appveterinario.domain.model.User;
import br.com.lutztechnology.appveterinario.domain.model.Veterinarian;
import br.com.lutztechnology.appveterinario.domain.repository.RoleRepository;
import br.com.lutztechnology.appveterinario.domain.repository.UserRepository;
import br.com.lutztechnology.appveterinario.domain.repository.VeterinarianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private VeterinarianRepository veterinarianRepository;

    @GetMapping
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("admin/users/index");
        Map<String, Object> newAttributes = new HashMap<>();
        List<User> users = userRepository.findAll();

        newAttributes.put("title", "Usuários");
        newAttributes.put("isAdmin", true);
        newAttributes.put("users", users);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/{id}")
    public ModelAndView details(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/users/details");
        Map<String, Object> newAttributes = new HashMap<>();
        User user = userRepository.getOne(id);

        newAttributes.put("title", "Detalhes do Usuário");
        newAttributes.put("isAdmin", true);
        newAttributes.put("user", user);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("/{id}/change-availability")
    public ModelAndView changeActive(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:admin/users");
        User user = userRepository.getOne(id);

        user.setActive(!user.getActive());

        userRepository.save(user);

        return modelAndView;
    }

    @GetMapping("/insert")
    public ModelAndView insert() {
        ModelAndView modelAndView = new ModelAndView("admin/users/insert");
        Map<String, Object> newAttributes = new HashMap<>();
        List<Role> roles = roleRepository.findAll();

        newAttributes.put("title", "Adicionar novo usuário");
        newAttributes.put("isAdmin", true);
        newAttributes.put("user", new User());
        newAttributes.put("roles", roles);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @PostMapping("/insert")
    public ModelAndView insert(
            @ModelAttribute(name = "user") User newUser) {

        ModelAndView modelAndView = new ModelAndView("redirect:admin/users");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isVet = false;

        for (Role role : newUser.getRoles()) {
            if (role.getType().equals("VET")) {
                isVet = true;
                break;
            }
        }

        if (isVet) {
            Veterinarian veterinarianAttributes = new Veterinarian(
                    newUser.getVeterinarian().getSpecialty(),
                    newUser.getVeterinarian().getCrmvState(),
                    newUser.getVeterinarian().getCrmv()
            );
            veterinarianAttributes = veterinarianRepository.save(veterinarianAttributes);
            newUser.setVeterinarian(veterinarianAttributes);
        } else {
            newUser.setVeterinarian(null);
        }

        newUser.setPassword(encoder.encode(newUser.getPassword()));
        userRepository.save(newUser);

        return modelAndView;
    }

    @GetMapping("/{id}/update")
    public ModelAndView update(@PathVariable(name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("admin/users/update");
        Map<String, Object> newAttributes = new HashMap<>();
        List<Role> roles = roleRepository.findAll();
        User userToUpdate = userRepository.getOne(id);
        boolean isVet = false;

        for (Role role : userToUpdate.getRoles()) {
            if (role.getType().equals("VET")) {
                isVet = true;
                break;
            }
        }

        newAttributes.put("title", "Editar usuário");
        newAttributes.put("isAdmin", true);
        newAttributes.put("user", userToUpdate);
        newAttributes.put("roles", roles);
        newAttributes.put("isVet", isVet);

        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @PostMapping("/{id}/update")
    public ModelAndView update(
            @ModelAttribute(name = "user") User userToUpdate,
            @PathVariable(name = "id") Long id) {

        ModelAndView modelAndView = new ModelAndView("redirect:admin/users");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User originalUser = userRepository.getOne(id);
        Set<Role> roles = userToUpdate.getRoles();
        boolean isVet = false;
        boolean isUser = false;

        for (Role role : userToUpdate.getRoles()) {
            if (role.getType().equals("VET")) {
                isVet = true;
                break;
            }
            isUser = true;
        }

        for (Role role : originalUser.getRoles()) {
            if (role.getType().equals("USER") && isVet) {
                // Se era user e virou vet
                Veterinarian veterinarian = new Veterinarian(
                        userToUpdate.getVeterinarian().getSpecialty(),
                        userToUpdate.getVeterinarian().getCrmvState(),
                        userToUpdate.getVeterinarian().getCrmv()
                );
                veterinarian = veterinarianRepository.save(veterinarian);
                userToUpdate.setVeterinarian(veterinarian);
            } else if (role.getType().equals("VET") && isUser) {
                // Se era vet e virou user
                Veterinarian veterinarian = originalUser.getVeterinarian();
                veterinarianRepository.delete(veterinarian);
                userToUpdate.setVeterinarian(null);
            }
        }

        // Se manteve
        if (userToUpdate.getPassword().isEmpty()) {
            User aux = userRepository.getOne(id);
            userToUpdate.setPassword(aux.getPassword());
        }
        userToUpdate.setPassword(encoder.encode(userToUpdate.getPassword()));

        userRepository.save(userToUpdate);

        return modelAndView;
    }

    @GetMapping(value = "/search", produces = "application/json")
    public @ResponseBody
    List<User> search(@RequestParam(name = "name") String username) {
        return userRepository.findByFirstNameOrLastNameOrUsername(username);
    }
}
