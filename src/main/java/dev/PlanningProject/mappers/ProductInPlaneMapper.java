package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductInPlaneMapper {

    //todo добавить логику с linkedProduct
    @Mapping(target = "task.id", source = "taskId")
    ProductInPlaneEntity toProductInPlaneEntity(ProductInPlaneDto productInPlaneDto);

    @Mapping(target = "taskId", source = "task.id")
    ProductInPlaneDto toProductInPLaneDto(ProductInPlaneEntity productInPlaneEntity);

}
