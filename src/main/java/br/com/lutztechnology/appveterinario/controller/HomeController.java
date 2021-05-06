package br.com.lutztechnology.appveterinario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("index");
        Map<String, Object> newAttributes = new HashMap<>();

        newAttributes.put("title", "Página inicial");
        newAttributes.put("isHome", true);
        modelAndView.addAllObjects(newAttributes);

        return modelAndView;
    }

    @GetMapping("login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");

        modelAndView.addObject("title", "Login");

        return modelAndView;
    }
}
