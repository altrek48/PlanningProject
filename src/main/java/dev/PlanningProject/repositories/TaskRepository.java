package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query("SELECT t FROM TaskEntity t WHERE t.groupId = :groupId ORDER BY t.id ASC")
    List<TaskEntity> findAllByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT COUNT(t) > 0 FROM TaskEntity t WHERE t.groupId = :groupId AND t.id = :taskId")
    boolean isTaskInGroup(@Param("groupId") Long groupId, @Param("taskId") Long taskId);

    @Query("SELECT COUNT(t) > 0 FROM TaskEntity t WHERE t.userCreator.linkedUserCredentials.username = :username AND t.id = :taskId")
    boolean isTaskCreator(@Param("taskId") Long taskId, @Param("username") String username);

    @Query("SELECT t FROM TaskEntity t LEFT JOIN FETCH t.products WHERE t.id = :id")
    Optional<TaskEntity> findByIdWithProducts(@Param("id") Long id);

}
