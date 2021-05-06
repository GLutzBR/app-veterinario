package br.com.lutztechnology.appveterinario.domain.repository;

import br.com.lutztechnology.appveterinario.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.fullName LIKE %:name%")
    List<Customer> findByFullName(@Param("name") String name);
}
