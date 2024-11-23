package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductDto;
import dev.PlanningProject.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    //todo переделать
    @Mapping(target = "purchase", ignore = true)
    @Mapping(target = "productInPlane", ignore = true)
    ProductEntity toProductEntity(ProductDto productDto);

//    @Mapping(target = "productInPlane", ignore = true)
//    ProductDto toProductDto(ProductEntity productEntity);

}
