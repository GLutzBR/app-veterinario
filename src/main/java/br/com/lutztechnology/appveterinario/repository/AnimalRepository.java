package br.com.lutztechnology.appveterinario.repository;

import br.com.lutztechnology.appveterinario.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("SELECT a FROM Animal a WHERE a.name LIKE %:name%")
    List<Animal> findByName(@Param("name") String name);

    @Query("SELECT a FROM Animal a WHERE a.owner.id = :id")
    List<Animal> findByOwnerId(@Param("id") Long id);
}
