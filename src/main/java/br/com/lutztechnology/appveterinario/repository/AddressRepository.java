package br.com.lutztechnology.appveterinario.repository;

import br.com.lutztechnology.appveterinario.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
