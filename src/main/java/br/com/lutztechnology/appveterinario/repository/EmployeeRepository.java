package br.com.lutztechnology.appveterinario.repository;

import br.com.lutztechnology.appveterinario.model.Employee;
import br.com.lutztechnology.appveterinario.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:search% OR e.email LIKE %:search%")
    List<Employee> findByNameOrEmail(String search);

    List<Employee> findByRole(Role role);
}
