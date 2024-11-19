package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProductInPlaneMapper.class)
public interface ListProductInPlaneMapper {

    List<ProductInPlaneEntity> toListProductInPlaneEntity(List<ProductInPlaneDto> listProductInPlaneDto);

    List<ProductInPlaneDto> toListProductInPlaneDto(List<ProductInPlaneEntity> productInPlaneEntityList);
}
