package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.services.ProductInPlaneService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductInPlaneRepository extends JpaRepository<ProductInPlaneEntity, Long> {

    @Query("SELECT s FROM ProductInPlaneEntity s WHERE s.task.id = :taskId")
    List<ProductInPlaneEntity> getProductsInPlaneByTaskId(@Param("taskId") Long taskId);

    //дописать
    void deleteProductInPlaneByTaskId(@Param("taskId") Long taskId);
}
