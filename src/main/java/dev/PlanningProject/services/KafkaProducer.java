package dev.PlanningProject.services;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaProducer {

    @Value("${service.name}")
    private String serviceName;

    @Value("${topic.name}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final GroupMapper groupMapper;
    private final TaskMapper taskMapper;
    private final PurchaseMapper purchaseMapper;

    public void eventBuilder(Object object, String action, String overrideClassName,  String exception) throws Exception {
        String entityClass;
        if(Objects.equals(overrideClassName, "")) {
            entityClass = object.getClass().getSimpleName();
        }
        else {
            entityClass = overrideClassName;
        }
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            AuditEvent auditEvent = new AuditEvent(entityClass, action, LocalDateTime.now(), object, username, serviceName, exception);
            log.info("auditEvent: " + auditEvent);
            kafkaTemplate.send(topicName, objectMapper.writeValueAsString(auditEvent));
        }
        //Нужно для работы с Initializer и тестами
        catch (NullPointerException nullPointerException) {
            AuditEvent auditEvent = new AuditEvent(entityClass, action, LocalDateTime.now(), object, "Initializer", serviceName, exception);
            log.info("auditEvent: " + auditEvent);
            kafkaTemplate.send(topicName, objectMapper.writeValueAsString(auditEvent));
        }
    }

}
