package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductInPlaneMapper {

    @Mapping(target = "linkedProduct", ignore = true)
    @Mapping(target = "task.id", source = "task_id")
    ProductInPlaneEntity toProductInPlaneEntity(ProductInPlaneDto productInPlaneDto);

    @Mapping(target = "linkedProduct_id", ignore = true)
    @Mapping(target = "task_id", source = "task.id")
    ProductInPlaneDto toProductInPLaneDto(ProductInPlaneEntity productInPlaneEntity);

}
