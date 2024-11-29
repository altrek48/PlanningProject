package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductDto;
import dev.PlanningProject.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProductMapper.class)
public interface ListProductMapper {


    List<ProductEntity> toListProductEntity(List<ProductDto> productDtoList);

    List<ProductDto> toListProductDto(List<ProductEntity> productEntityList);

}
