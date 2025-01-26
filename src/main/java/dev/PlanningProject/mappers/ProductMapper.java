package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(target = "purchase.id", source = "purchaseId")
    ProductEntity toProductEntity(ProductDto productDto);

    @Mapping(target = "productInPlaneId", source = "productInPlane.id")
    @Mapping(target = "purchaseId", source = "purchase.id")
    ProductDto toProductDto(ProductEntity productEntity);

}
