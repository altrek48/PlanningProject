package dev.PlanningProject.repositories;

import dev.PlanningProject.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Query("SELECT p FROM ProductEntity p WHERE p.id IN :productsIds")
    List<ProductEntity> getProductsByIds(@Param("productsIds") List<Long> productsIds);

}
