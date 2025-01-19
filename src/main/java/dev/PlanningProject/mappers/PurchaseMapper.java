package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductMapper.class)
public interface PurchaseMapper {

    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "date", source = "now")
    @Mapping(target = "userPayer", source = "userPayer")
    @Mapping(target = "id", ignore = true) //id генерируется автоматически(без этого маппинга вылетает ошибка)
    @Mapping(target = "amount", source = "amount")
    //@Mapping(target = "task.id", source = "taskId")
    PurchaseEntity toPurchaseEntity(PurchaseDto purchaseDto, Long groupId, LocalDateTime now, UserEntity userPayer, BigDecimal amount);

    @Mapping(target = "userPayer", source = "userPayer.linkedUserCredentials.username")
    @Mapping(target = "taskId", source = "linkedTask.id")
    PurchaseDto toPurchaseDto(PurchaseEntity purchaseEntity);

    @Mapping(target = "userPayer", ignore = true)
    //@Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "groupId", source = "group.id")
    PurchaseDto toPurchaseDtoWithPlane(PurchaseEntity purchaseEntity);

    @Mapping(target = "userPayer", source = "userPayer.linkedUserCredentials.username")
    PurchaseShortDto toPurchaseShortDto(PurchaseEntity purchaseEntity);
}
