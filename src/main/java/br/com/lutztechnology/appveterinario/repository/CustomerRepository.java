package br.com.lutztechnology.appveterinario.repository;

import br.com.lutztechnology.appveterinario.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.name LIKE %:name%")
    List<Customer> findByName(String name);
}
