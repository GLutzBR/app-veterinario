package br.com.lutztechnology.appveterinario.repository;

import br.com.lutztechnology.appveterinario.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByName(@Param("name") String name);
}
