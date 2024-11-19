package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductDto;
import dev.PlanningProject.entities.ProductEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T15:45:52+0300",
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

        productEntity.setId( productDto.getId() );
        productEntity.setName( productDto.getName() );
        productEntity.setQuantity( productDto.getQuantity() );
        productEntity.setPrice( productDto.getPrice() );

        return productEntity;
    }
}
