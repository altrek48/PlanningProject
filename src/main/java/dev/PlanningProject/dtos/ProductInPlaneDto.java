package dev.PlanningProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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

    @NotBlank
    @Size(min = 2, max = 36, message = "Name must be between 2 and 36 characters long")
    private String name;

    private Boolean completeness = false;

    private BigDecimal price;

    private Long taskId;

    private Long linkedProductId;
}
