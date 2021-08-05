package br.com.lutztechnology.appveterinario.exceptions;

public class RoleHasEmployeesException extends  RuntimeException {

    public RoleHasEmployeesException(Long id) {
        super(String.format("Role with ID %s has employee(s) linked.", id));
    }
}
