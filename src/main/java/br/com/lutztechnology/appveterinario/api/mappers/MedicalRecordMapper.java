package br.com.lutztechnology.appveterinario.api.mappers;

import br.com.lutztechnology.appveterinario.api.dto.MedicalRecordDTO;
import br.com.lutztechnology.appveterinario.model.MedicalRecord;
import br.com.lutztechnology.appveterinario.services.AnimalService;
import br.com.lutztechnology.appveterinario.services.CustomerService;
import br.com.lutztechnology.appveterinario.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicalRecordMapper {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AnimalService animalService;

    @Autowired
    private EmployeeService employeeService;

    public MedicalRecord convertToEntity(MedicalRecordDTO medicalRecordDTO) {
        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setServiceDate(medicalRecordDTO.getServiceDate());
        medicalRecord.setComments(medicalRecordDTO.getComments());
        medicalRecord.setArchived(medicalRecordDTO.getArchived());
        medicalRecord.setCustomer(customerService.searchById(medicalRecordDTO.getCustomerId()));
        medicalRecord.setAnimal(animalService.searchById(medicalRecordDTO.getAnimalId()));
        medicalRecord.setVeterinarian(employeeService.searchById(medicalRecordDTO.getVeterinarianId()));

        return medicalRecord;
    }
}
