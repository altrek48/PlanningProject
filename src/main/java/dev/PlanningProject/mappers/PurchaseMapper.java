package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.entities.PurchaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductMapper.class)
public interface PurchaseMapper {
    //todo переделать

    @Mapping(target = "userPayer", ignore = true)
    @Mapping(target = "groupId", source = "groupId")
    //@Mapping(target = "task.id", source = "taskId")
    PurchaseEntity toPurchaseEntity(PurchaseDto purchaseDto, Long groupId);

    @Mapping(target = "userPayer", source = "userPayer.linkedUserCredentials.username")
    //@Mapping(target = "taskId", source = "task.id")
    PurchaseDto toPurchaseDto(PurchaseEntity purchaseEntity);

    @Mapping(target = "userPayer", ignore = true)
    //@Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "groupId", source = "group.id")
    PurchaseDto toPurchaseDtoWithPlane(PurchaseEntity purchaseEntity);

    @Mapping(target = "userPayer", source = "userPayer.linkedUserCredentials.username")
    PurchaseShortDto toPurchaseShortDto(PurchaseEntity purchaseEntity);
}
