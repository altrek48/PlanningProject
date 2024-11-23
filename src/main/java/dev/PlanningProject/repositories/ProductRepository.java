package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT s FROM ProductEntity s WHERE s.purchase.id = :purchaseId")
    List<ProductEntity> getProductsByPurchaseId(@Param("purchaseId") Long purchaseId);

}
