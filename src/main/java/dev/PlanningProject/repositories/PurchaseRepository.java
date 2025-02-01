package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.PurchaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {

    @Query("SELECT p FROM PurchaseEntity p WHERE p.groupId = :groupId ORDER BY p.id DESC")
    List<PurchaseEntity> findAllByGroupId(@Param("groupId") Long groupId);

    @Query("SELECT COUNT(p) > 0 FROM PurchaseEntity p WHERE p.groupId = :groupId AND p.id = :purchaseId")
    boolean isPurchaseInGroup(@Param("groupId") Long groupId, @Param("purchaseId") Long purchaseId);

    @Query("SELECT p.id FROM PurchaseEntity p JOIN p.products pr WHERE pr.id = :productId ")
    Optional<Long> findPurchaseIdByProductId(@Param("productId") Long productId);

}

