package dev.PlanningProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseDto {

    private Long id;

    private String storeName;

    private LocalDateTime date;

    private BigDecimal amount;

    private Long groupId;

    private Long taskId;

    @NotEmpty
    private List<ProductDto> products;

    private String userPayer;

}
