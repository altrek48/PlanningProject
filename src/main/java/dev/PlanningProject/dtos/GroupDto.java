package dev.PlanningProject.dtos;

import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.TaskEntity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupDto {

    private Long id;

    private String name;

    private List<PurchaseDto> purchases;

    private List<TaskDto> tasks;

}
