package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.repositories.ProductRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ProductInPlaneMapper.class)
public interface ListProductInPlaneMapper {

    List<ProductInPlaneEntity> toListProductInPlaneEntity(List<ProductInPlaneDto> listProductInPlaneDto);

    @Named("withProductRepository")
    List<ProductInPlaneEntity> toListProductInPlaneEntity(List<ProductInPlaneDto> listProductInPlaneDto, @Context ProductRepository productRepository);

    List<ProductInPlaneDto> toListProductInPlaneDto(List<ProductInPlaneEntity> productInPlaneEntityList);
}
