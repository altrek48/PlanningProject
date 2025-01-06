package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    //связанные покупки и задачи не учитываются
    @Query("SELECT DISTINCT g FROM GroupEntity g JOIN g.users u WHERE u.linkedUserCredentials.username = :username")
    List<GroupEntity> findGroupsByUsername(@Param("username") String username);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u JOIN u.groups g WHERE u.linkedUserCredentials.username = :username AND g.id = :groupId")
    boolean isUserInGroup(String username, Long groupId);
}
