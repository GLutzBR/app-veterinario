package br.com.lutztechnology.appveterinario.domain.repository;

import br.com.lutztechnology.appveterinario.domain.model.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long> {
}
