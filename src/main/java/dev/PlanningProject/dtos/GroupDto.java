package dev.PlanningProject.dtos;

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
