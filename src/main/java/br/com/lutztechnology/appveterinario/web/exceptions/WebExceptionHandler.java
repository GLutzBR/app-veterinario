package br.com.lutztechnology.appveterinario.web.exceptions;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice(annotations = Controller.class)
public class WebExceptionHandler implements ErrorViewResolver {
    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
        ModelAndView modelAndView = new ModelAndView("errors/exceptions");

        modelAndView.addObject("status", status.value());

        switch (status.value()) {
            case 400:
                modelAndView.addObject("causeTitle", "Requisição mal feita.");
                modelAndView.addObject("message", "O servidor não pôde processar a requisição devido a alguma coisa que foi entendida como um erro do cliente!");
                modelAndView.addObject("cause", "A url para página '" + model.get("path") + "' não existe.");
                modelAndView.addObject("cssClass", "text-warning");
                break;
            case 404:
                modelAndView.addObject("causeTitle", "Página não encontrada.");
                modelAndView.addObject("message", "A página que você procura não existe!");
                modelAndView.addObject("cause", "A url para página '" + model.get("path") + "' não existe.");
                modelAndView.addObject("cssClass", "text-warning");
                break;
            case 500:
                modelAndView.addObject("causeTitle", "Erro interno no servidor.");
                modelAndView.addObject("message", "Alguma coisa deu errado.");
                modelAndView.addObject("cause", "Ocorreu um  erro inesperado, tente mais tarde.");
                modelAndView.addObject("cssClass", "text-danger");
                break;
        }

        return modelAndView;
    }
}
