package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByGroupId(Long groupId);

    @Query("SELECT COUNT(t) > 0 FROM TaskEntity t WHERE t.groupId = :groupId AND t.id = :taskId")
    boolean isTaskInGroup(Long groupId, Long taskId);

}
