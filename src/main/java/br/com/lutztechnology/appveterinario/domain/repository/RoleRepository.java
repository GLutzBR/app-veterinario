package br.com.lutztechnology.appveterinario.domain.repository;

import br.com.lutztechnology.appveterinario.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name LIKE %:name% OR r.type LIKE %:name%" +
            " GROUP BY r.id")
    List<Role> findByNameOrType(@Param("name") String name);
}
