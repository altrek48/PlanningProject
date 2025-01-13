package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.CredentialsEntity;
import dev.PlanningProject.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends CrudRepository<CredentialsEntity, Long> {

    @Query("SELECT u FROM CredentialsEntity u JOIN FETCH u.password WHERE u.username = :username")
    Optional<CredentialsEntity> findByUsername(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 FROM CredentialsEntity u WHERE LOWER(u.username) = LOWER(:username)")
    boolean checkAvailableUsername(@Param("username") String username);

    @Query("SELECT u FROM UserEntity u WHERE u.linkedUserCredentials.username = :username")
    Optional<UserEntity> getUserByUsername(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 From UserEntity u JOIN u.groups g WHERE u.linkedUserCredentials.username = :username AND g.id = :groupId")
    boolean isUserConsistsInGroup(@Param("groupId") Long groupId, @Param("username") String username);

}
