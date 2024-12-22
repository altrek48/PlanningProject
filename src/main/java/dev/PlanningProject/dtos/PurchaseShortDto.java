package dev.PlanningProject.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseShortDto {
    private Long id;

    private String storeName;

    private LocalDateTime date;

    private BigDecimal amount;

    private Long groupId;

    private Long taskId;
}
