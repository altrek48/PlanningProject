package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductInPlaneMapper {

    //todo переделать
    @Mapping(target = "linkedProduct", ignore = true)
    @Mapping(target = "task.id", source = "taskId")
    ProductInPlaneEntity toProductInPlaneEntity(ProductInPlaneDto productInPlaneDto);

    @Mapping(target = "linkedProductId", ignore = true)
    @Mapping(target = "taskId", source = "task.id")
    ProductInPlaneDto toProductInPLaneDto(ProductInPlaneEntity productInPlaneEntity);

}
