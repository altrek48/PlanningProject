package dev.PlanningProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    public PurchaseDto(String storeName, String userPayer) {
        this.storeName = storeName;
        this.userPayer = userPayer;
    }

    private Long id;

    @NotBlank
    @Size(min = 2, max = 36, message = "storeName must be between 2 and 36 characters long")
    private String storeName;

    private LocalDateTime date;

    private BigDecimal amount;

    private Long groupId;

    private Long taskId;

    @NotEmpty
    private List<ProductDto> products;

    private String userPayer;

}
