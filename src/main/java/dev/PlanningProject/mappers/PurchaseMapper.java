package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.entities.PurchaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductMapper.class)
public interface PurchaseMapper {
    //todo переделать
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "task", ignore = true)
    PurchaseEntity toPurchaseEntity(PurchaseDto purchaseDto);
    //todo переделать
    @Mapping(target = "taskId", ignore = true)
    @Mapping(target = "groupId", source = "group.id")
    PurchaseDto toPurchaseDto(PurchaseEntity purchaseEntity);
    //todo переделать
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "groupId", source = "group.id")
    PurchaseDto toPurchaseDtoWithPlane(PurchaseEntity purchaseEntity);
}
