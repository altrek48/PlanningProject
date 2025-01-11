package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

    List<PurchaseEntity> findAllByGroupId(Long groupId);

    @Query("SELECT COUNT(p) > 0 FROM PurchaseEntity p WHERE p.groupId = :groupId AND p.id = :purchaseId")
    boolean isPurchaseInGroup(@Param("groupId") Long groupId, @Param("purchaseId") Long purchaseId);

}

