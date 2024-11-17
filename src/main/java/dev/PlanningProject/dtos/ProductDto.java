package dev.PlanningProject.dtos;

import dev.PlanningProject.entities.PurchaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDto {

    private Long id;

    private String name;

    private Long quantity;

    private BigDecimal price;

    private PurchaseDto purchase;

}
