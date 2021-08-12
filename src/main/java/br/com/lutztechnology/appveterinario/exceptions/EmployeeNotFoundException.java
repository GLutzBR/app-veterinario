package br.com.lutztechnology.appveterinario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EmployeeNotFoundException extends EntityNotFoundException {

    public EmployeeNotFoundException(Long id) {
        super(String.format("Employee with ID %s not found", id));
    }
    public EmployeeNotFoundException(String email) {
        super(String.format("Employee with ID %s not found", email));
    }
}
