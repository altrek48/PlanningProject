package dev.PlanningProject.dtos;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class TaskDto {

    private Long id;

    private String name;

    private String comment;

    private BigDecimal amount;

    private List<ProductInPlaneDto> products;

    private List<PurchaseDto> purchases;

    private GroupDto group;

}
