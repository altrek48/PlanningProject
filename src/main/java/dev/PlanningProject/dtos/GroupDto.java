package dev.PlanningProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupDto {

    private Long id;

    @NotBlank(message = "name may not be blank")
    @Size(min = 2, max = 36, message = "Name must be between 2 and 36 characters long")
    private String name;

}
