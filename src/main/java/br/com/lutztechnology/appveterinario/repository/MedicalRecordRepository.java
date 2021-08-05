package br.com.lutztechnology.appveterinario.repository;

import br.com.lutztechnology.appveterinario.model.Employee;
import br.com.lutztechnology.appveterinario.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    List<MedicalRecord> findByAnimalName(String patient);

    List<MedicalRecord> findByVeterinarian(Employee employee);
}
