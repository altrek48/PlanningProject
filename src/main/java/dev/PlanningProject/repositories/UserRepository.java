package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.UserCredentialsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserCredentialsEntity, Long> {

    @Query("SELECT u FROM UserCredentialsEntity u JOIN FETCH u.password WHERE u.username = :name")
    Optional<UserCredentialsEntity> findByUsername(String name);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM UserCredentialsEntity u WHERE LOWER(u.username) = LOWER(:username)")
    Boolean checkAvailableUsername(@Param("username") String username);

}
