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
public class TaskDto {

    private Long id;

    private String name;

    private String comment;

    private BigDecimal amount;

    private List<ProductInPlaneDto> products;

    private List<Long> purchasesIds;

    private Long groupId;

}

