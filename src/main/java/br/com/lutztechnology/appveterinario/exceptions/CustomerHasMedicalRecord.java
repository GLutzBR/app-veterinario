package br.com.lutztechnology.appveterinario.exceptions;

public class CustomerHasMedicalRecord extends RuntimeException {

    public CustomerHasMedicalRecord(Long id) {
        super(String.format("Customer with ID %s has medical records linked.", id));
    }
}
