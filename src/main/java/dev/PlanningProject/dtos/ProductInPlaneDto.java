package dev.PlanningProject.dtos;

import dev.PlanningProject.entities.TaskEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInPlaneDto {



    public ProductInPlaneDto(String name) {
        this.name = name;
        this.completeness = false;
    }

    private Long id;

    private String name;

    private Boolean completeness;

    private TaskDto task;
}
