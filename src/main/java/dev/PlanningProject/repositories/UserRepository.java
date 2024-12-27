package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.UserCredentialsEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserCredentialsEntity, Long> {

    @Query("SELECT u FROM UserCredentialsEntity u JOIN FETCH u.password WHERE u.username = :name")
    Optional<UserCredentialsEntity> findByUsername(String name);

}
