package dev.PlanningProject.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dev.PlanningProject.dtos.AuditEvent;
import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.mappers.GroupMapper;
import dev.PlanningProject.mappers.PurchaseMapper;
import dev.PlanningProject.mappers.TaskMapper;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {

    @Value("${service.name}")
    private String serviceName;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final GroupMapper groupMapper;
    private final TaskMapper taskMapper;
    private final PurchaseMapper purchaseMapper;


    @PostPersist
    private void afterPersist(Object object) throws Exception {
        log.info(objectMapper.writeValueAsString(eventBuilder(object, "PERSIST")));
        kafkaTemplate.send("user_actions", objectMapper.writeValueAsString(eventBuilder(object, "PERSIST")));
    }

    @PostUpdate
    private void afterUpdate(Object object) throws Exception {
        log.info(objectMapper.writeValueAsString(eventBuilder(object, "UPDATE")));
        kafkaTemplate.send("user_actions", objectMapper.writeValueAsString(eventBuilder(object, "UPDATE")));
    }

    @PostRemove
    private void afterRemove(Object object) throws Exception {
        log.info(objectMapper.writeValueAsString(eventBuilder(object, "REMOVE")));
        kafkaTemplate.send("user_actions", objectMapper.writeValueAsString(eventBuilder(object, "REMOVE")));
    }

    private AuditEvent eventBuilder(Object object, String action) {
        try {
            return AuditEvent.builder()
                    .username(SecurityContextHolder.getContext().getAuthentication().getName())
                    .entityClass(object.getClass().getSimpleName())
                    .object(toObjectDto(object))
                    .action(action)
                    .localDateTime(LocalDateTime.now())
                    .serviceName(serviceName)
                    .build();
        }
        catch (NullPointerException exception) {
            return AuditEvent.builder()
                    .username("initializer")
                    .entityClass(object.getClass().getSimpleName())
                    .object(toObjectDto(object))
                    .action(action)
                    .localDateTime(LocalDateTime.now())
                    .serviceName(serviceName)
                    .build();
        }
    }

    private Object toObjectDto(Object object) {
        return switch (object.getClass().getSimpleName()) {
            case "GroupEntity" -> groupMapper.toGroupDto((GroupEntity) object);
            case "TaskEntity" -> taskMapper.toTaskDto((TaskEntity) object);
            case "PurchaseEntity" -> purchaseMapper.toPurchaseDto((PurchaseEntity) object);
            default -> object;
        };
    }

}
