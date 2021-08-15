package br.com.lutztechnology.appveterinario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AnimalNotFoundException extends EntityNotFoundException {

    public AnimalNotFoundException(Long id) {
        super(String.format("Animal with ID %s not found.", id));
    }
}
