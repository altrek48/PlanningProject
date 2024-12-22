package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {
    List<PurchaseEntity> findAllByGroupId(Long groupId);
}
