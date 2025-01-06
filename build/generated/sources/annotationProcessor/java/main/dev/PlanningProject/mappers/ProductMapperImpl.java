package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-07T02:04:11+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductEntity toProductEntity(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setPurchase( productDtoToPurchaseEntity( productDto ) );
        productEntity.setId( productDto.getId() );
        productEntity.setName( productDto.getName() );
        productEntity.setQuantity( productDto.getQuantity() );
        productEntity.setPrice( productDto.getPrice() );

        return productEntity;
    }

    @Override
    public ProductDto toProductDto(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductDto.ProductDtoBuilder productDto = ProductDto.builder();

        productDto.purchaseId( productEntityPurchaseId( productEntity ) );
        productDto.id( productEntity.getId() );
        productDto.name( productEntity.getName() );
        productDto.quantity( productEntity.getQuantity() );
        productDto.price( productEntity.getPrice() );

        return productDto.build();
    }

    protected PurchaseEntity productDtoToPurchaseEntity(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        PurchaseEntity purchaseEntity = new PurchaseEntity();

        purchaseEntity.setId( productDto.getPurchaseId() );

        return purchaseEntity;
    }

    private Long productEntityPurchaseId(ProductEntity productEntity) {
        PurchaseEntity purchase = productEntity.getPurchase();
        if ( purchase == null ) {
            return null;
        }
        return purchase.getId();
    }
}
