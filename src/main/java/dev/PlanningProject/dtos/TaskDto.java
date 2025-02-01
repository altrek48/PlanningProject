package dev.PlanningProject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {

    public TaskDto(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public TaskDto(Long id, String name, String comment, Long groupId, List<ProductInPlaneDto> products) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.groupId = groupId;
        this.products = products;
    }

    private Long id;

    @NotBlank(message = "name may not be blank")
    @Size(min = 2, max = 36, message = "Name must be between 2 and 36 characters long")
    private String name;

    private String comment;

    private BigDecimal amount;

    private Integer completeness;

    private List<ProductInPlaneDto> products;

    private Long groupId;

    private String userCreator;

}

