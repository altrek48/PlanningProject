package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.repositories.ProductRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductInPlaneMapper {

    //todo -productRepository через service
    @Mapping(target = "task.id", source = "taskId")
    ProductInPlaneEntity toProductInPlaneEntity(ProductInPlaneDto productInPlaneDto, @Context ProductRepository productRepository);

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "linkedProductId", source = "linkedProduct.id")
    ProductInPlaneDto toProductInPLaneDto(ProductInPlaneEntity productInPlaneEntity);

    @AfterMapping
    default void afterMapping(ProductInPlaneDto productInPlaneDto, @MappingTarget ProductInPlaneEntity productInPlane, @Context ProductRepository productRepository) {
        if (productInPlaneDto.getLinkedProductId() != null) {
            ProductEntity linkedProduct = productRepository.findById(productInPlaneDto.getLinkedProductId())
                    .orElseThrow(() -> new UsernameNotFoundException("Product with id: " + productInPlaneDto.getLinkedProductId() + " not found"));
            productInPlane.setLinkedProduct(linkedProduct);
        }
    }

}
