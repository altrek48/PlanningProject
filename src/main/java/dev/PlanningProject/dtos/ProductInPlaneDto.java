package dev.PlanningProject.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInPlaneDto {



    public ProductInPlaneDto(String name) {
        this.name = name;
    }

    private Long id;

    private String name;

    private Boolean completeness = false;

    private BigDecimal price;

    private Long taskId;

    private Long linkedProductId;
}
