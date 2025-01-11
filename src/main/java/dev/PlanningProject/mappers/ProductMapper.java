package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductDto;
import dev.PlanningProject.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    //todo нужно сделать с productInPlane.id
    @Mapping(target = "purchase.id", source = "purchaseId")
    //@Mapping(target = "productInPlane.id", source = "productInPlaneId")
    ProductEntity toProductEntity(ProductDto productDto);

    @Mapping(target = "productInPlaneId", source = "productInPlane.id")
    @Mapping(target = "purchaseId", source = "purchase.id")
    ProductDto toProductDto(ProductEntity productEntity);

}
