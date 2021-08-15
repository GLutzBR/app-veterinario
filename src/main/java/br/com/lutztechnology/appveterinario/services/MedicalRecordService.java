package br.com.lutztechnology.appveterinario.services;

import br.com.lutztechnology.appveterinario.exceptions.MedicalRecordNotFoundException;
import br.com.lutztechnology.appveterinario.model.MedicalRecord;
import br.com.lutztechnology.appveterinario.repository.EmployeeRepository;
import br.com.lutztechnology.appveterinario.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedicalRecordService {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<MedicalRecord> searchAll() {
        return medicalRecordRepository
                .findAll()
                .stream()
                .filter(medicalRecord -> !medicalRecord.getArchived())
                .collect(Collectors.toList());
    }

    public List<MedicalRecord> searchArchived() {
        return medicalRecordRepository
                .findAll()
                .stream()
                .filter(MedicalRecord::getArchived)
                .collect(Collectors.toList());
    }

    public Page<MedicalRecord> searchAll(Pageable pageable) {
        return medicalRecordRepository.findAll(pageable);
    }

    public MedicalRecord searchById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordNotFoundException(id));
    }

    public List<MedicalRecord> searchByAnimalName(String patient) {
        return medicalRecordRepository.findByAnimalName(patient);
    }

    public MedicalRecord insert(MedicalRecord medicalRecord) {
        return medicalRecordRepository.save(medicalRecord);
    }

    public MedicalRecord archive(Long id) {
        MedicalRecord medicalRecord = searchById(id);

        medicalRecord.setArchived(!medicalRecord.getArchived());

        return medicalRecordRepository.save(medicalRecord);
    }
}
