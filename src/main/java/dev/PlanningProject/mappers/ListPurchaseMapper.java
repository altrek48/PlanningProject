package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.entities.PurchaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = PurchaseMapper.class)
public interface ListPurchaseMapper {
    List<PurchaseShortDto> toListPurchaseShortDto(List<PurchaseEntity> purchases);
}
