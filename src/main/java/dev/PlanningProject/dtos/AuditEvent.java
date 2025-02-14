package dev.PlanningProject.dtos;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditEvent {
    private String entityClass;
    private String action;
    private LocalDateTime localDateTime;
    private Object object;
    private String username;
    private String serviceName;
}
