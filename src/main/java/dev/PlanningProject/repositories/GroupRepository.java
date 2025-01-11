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

    @Query("SELECT COUNT(u) > 0 FROM UserEntity u JOIN u.groups g WHERE u.linkedUserCredentials.username = :username AND g.id = :groupId")
    boolean isUserInGroup(@Param("username") String username, @Param("groupId") Long groupId);

    @Query("SELECT COUNT(g) > 0 FROM GroupEntity g WHERE g.userCreator.linkedUserCredentials.username = :username AND g.id = :groupId")
    boolean isUserCreator(@Param("username") String username, @Param("groupId") Long groupId);
}
