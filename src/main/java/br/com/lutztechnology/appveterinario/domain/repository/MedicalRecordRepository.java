package br.com.lutztechnology.appveterinario.domain.repository;

import br.com.lutztechnology.appveterinario.domain.model.Employee;
import br.com.lutztechnology.appveterinario.domain.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    @Query("SELECT mr FROM MedicalRecord mr WHERE mr.animal.name LIKE %:patient%")
    List<MedicalRecord> findByPetName(@Param("patient") String patient);

    List<MedicalRecord> findByEmployee(Employee employee);
}
