package dev.PlanningProject.repositories;

import dev.PlanningProject.dtos.UserProfile;
import dev.PlanningProject.entities.CredentialsEntity;
import dev.PlanningProject.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CredentialsRepository extends CrudRepository<CredentialsEntity, Long> {

    @Query("SELECT u FROM CredentialsEntity u JOIN FETCH u.password WHERE u.username = :username")
    Optional<CredentialsEntity> findByUsername(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 FROM CredentialsEntity u WHERE LOWER(u.username) = LOWER(:username)")
    boolean checkAvailableUsername(@Param("username") String username);

    @Query("SELECT u FROM UserEntity u WHERE u.linkedUserCredentials.username = :username")
    Optional<UserEntity> getUserByUsername(@Param("username") String username);

    @Query("SELECT COUNT(u) > 0 From UserEntity u JOIN u.groups g WHERE u.linkedUserCredentials.username = :username AND g.id = :groupId")
    boolean isUserConsistsInGroup(@Param("groupId") Long groupId, @Param("username") String username);

    @Query("SELECT new dev.PlanningProject.dtos.UserProfile(u.id, c.username, u.email, u.avatarUrl) FROM UserEntity u JOIN u.linkedUserCredentials c WHERE c.username = :username")
    Optional<UserProfile> getUserProfile(@Param("username") String username);

    @Query("SELECT new dev.PlanningProject.dtos.UserProfile(u.id, c.username, u.email, u.avatarUrl) " +
            "FROM GroupEntity g JOIN g.users u JOIN u.linkedUserCredentials c WHERE g.id = :groupId")
    List<UserProfile> getProfilesByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT u FROM UserEntity u LEFT JOIN FETCH u.groups WHERE u.linkedUserCredentials.username = :username")
    Optional<UserEntity> getUserByUsernameWithGroups(@Param("username") String username);
}
