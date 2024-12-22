package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductDto;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-22T16:45:04+0300",
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

        ProductDto productDto = new ProductDto();

        productDto.setPurchaseId( productEntityPurchaseId( productEntity ) );
        productDto.setId( productEntity.getId() );
        productDto.setName( productEntity.getName() );
        productDto.setQuantity( productEntity.getQuantity() );
        productDto.setPrice( productEntity.getPrice() );

        return productDto;
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
