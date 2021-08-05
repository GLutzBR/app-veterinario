package br.com.lutztechnology.appveterinario.domain.repository;

import br.com.lutztechnology.appveterinario.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
