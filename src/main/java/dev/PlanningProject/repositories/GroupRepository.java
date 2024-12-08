package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    //связанные покупки и задачи не учитываются
    @Query("SELECT new GroupEntity(g.id, g.name) FROM GroupEntity g")
    List<GroupEntity> findAllShorted();

}
