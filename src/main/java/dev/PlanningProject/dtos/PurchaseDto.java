package dev.PlanningProject.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseDto {

    private Long id;

    private String storeName;

    //private Date date;

    private BigDecimal amount;

    private Long groupId;

    private Long taskId;

    private List<ProductDto> products;

}
