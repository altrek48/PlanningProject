package dev.PlanningProject.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskShortDto {

        private Long id;
        private String name;
        private String comment;
        private BigDecimal amount;
        private Integer completeness;
}
