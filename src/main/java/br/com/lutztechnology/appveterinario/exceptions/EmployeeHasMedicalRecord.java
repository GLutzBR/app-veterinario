package br.com.lutztechnology.appveterinario.exceptions;

public class EmployeeHasMedicalRecord extends RuntimeException {

    public EmployeeHasMedicalRecord(Long id) {
        super(String.format("Employee with ID %s already has a Medical Record linked.", id));
    }
}
