package dev.PlanningProject.dtos;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.TaskEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PurchaseDto {

    private Long id;

    private String storeName;

    //private Date date;

    private BigDecimal amount;

    private GroupDto group;

    private TaskDto task;

    List<ProductDto> products;

}
