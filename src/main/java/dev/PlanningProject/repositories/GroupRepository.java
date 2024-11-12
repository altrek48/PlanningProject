package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
