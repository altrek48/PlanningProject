package dev.PlanningProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    public ProductDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    private Long id;

    @NotBlank
    @Size(min = 2, max = 36, message = "Name must be between 2 and 36 characters long")
    private String name;

    private Long quantity;

    @NotNull
    private BigDecimal price;

    private Long purchaseId;

    private Long productInPlaneId;
}
