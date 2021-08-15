package br.com.lutztechnology.appveterinario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AppRoleNotFoundException extends EntityNotFoundException {

    public AppRoleNotFoundException(Long id) {
        super(String.format("Role with ID %s not found.", id));
    }
}
