package dev.PlanningProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductInPlaneDto {



    public ProductInPlaneDto(String name) {
        this.name = name;
        this.completeness = false;
    }

    private Long id;

    @NotBlank
    @Size(min = 2, max = 36, message = "Name must be between 2 and 36 characters long")
    private String name;

    private Boolean completeness;

    private BigDecimal price;

    private Long taskId;

    private Long linkedProductId;
}
