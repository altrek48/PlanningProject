package dev.PlanningProject.dtos;

import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.TaskEntity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {

    public GroupDto(String name) {
        this.name = name;
    }

    private Long id;

    private String name;

    private List<Long> purchasesIds;

    private List<Long> tasksIds;

}
