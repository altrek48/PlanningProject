package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.entities.PurchaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductMapper.class)
public interface PurchaseMapper {

    @Mapping(target = "group", ignore = true)
    @Mapping(target = "task", ignore = true)
    PurchaseEntity toPurchaseEntity(PurchaseDto purchaseDto);

    @Mapping(target = "task_id", ignore = true)
    @Mapping(target = "group_id", source = "group.id")
    PurchaseDto toPurchaseDto(PurchaseEntity purchaseEntity);

    @Mapping(target = "task_id", source = "task.id")
    @Mapping(target = "group_id", source = "group.id")
    PurchaseDto toPurchaseDtoWithPlane(PurchaseEntity purchaseEntity);
}
