package br.com.lutztechnology.appveterinario.domain.repository;

import br.com.lutztechnology.appveterinario.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.firstName LIKE %:name% OR u.lastName LIKE %:name% OR u.username LIKE %:name%" +
            " GROUP BY u.id")
    List<User> findByFirstNameOrLastNameOrUsername(@Param("name") String name);
}
