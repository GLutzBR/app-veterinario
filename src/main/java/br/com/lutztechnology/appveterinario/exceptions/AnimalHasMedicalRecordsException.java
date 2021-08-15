package br.com.lutztechnology.appveterinario.exceptions;

public class AnimalHasMedicalRecordsException extends RuntimeException {

    public AnimalHasMedicalRecordsException(Long id) {
        super(String.format("Animal with ID %s has medical records linked", id));
    }
}
