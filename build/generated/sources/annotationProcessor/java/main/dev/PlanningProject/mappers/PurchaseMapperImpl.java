package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductDto;
import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.ProductEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-19T15:45:52+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class PurchaseMapperImpl implements PurchaseMapper {

    @Autowired
    private ListProductMapper listProductMapper;

    @Override
    public PurchaseEntity toPurchaseEntity(PurchaseDto purchaseDto) {
        if ( purchaseDto == null ) {
            return null;
        }

        PurchaseEntity purchaseEntity = new PurchaseEntity();

        purchaseEntity.setId( purchaseDto.getId() );
        purchaseEntity.setStoreName( purchaseDto.getStoreName() );
        purchaseEntity.setAmount( purchaseDto.getAmount() );
        purchaseEntity.setProducts( listProductMapper.toListProductEntity( purchaseDto.getProducts() ) );

        return purchaseEntity;
    }

    @Override
    public PurchaseDto toPurchaseDto(PurchaseEntity purchaseEntity) {
        if ( purchaseEntity == null ) {
            return null;
        }

        PurchaseDto purchaseDto = new PurchaseDto();

        purchaseDto.setGroup_id( purchaseEntityGroupId( purchaseEntity ) );
        purchaseDto.setId( purchaseEntity.getId() );
        purchaseDto.setStoreName( purchaseEntity.getStoreName() );
        purchaseDto.setAmount( purchaseEntity.getAmount() );
        purchaseDto.setProducts( productEntityListToProductDtoList( purchaseEntity.getProducts() ) );

        return purchaseDto;
    }

    private Long purchaseEntityGroupId(PurchaseEntity purchaseEntity) {
        GroupEntity group = purchaseEntity.getGroup();
        if ( group == null ) {
            return null;
        }
        return group.getId();
    }

    protected ProductDto productEntityToProductDto(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        productDto.setId( productEntity.getId() );
        productDto.setName( productEntity.getName() );
        productDto.setQuantity( productEntity.getQuantity() );
        productDto.setPrice( productEntity.getPrice() );

        return productDto;
    }

    protected List<ProductDto> productEntityListToProductDtoList(List<ProductEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<ProductDto> list1 = new ArrayList<ProductDto>( list.size() );
        for ( ProductEntity productEntity : list ) {
            list1.add( productEntityToProductDto( productEntity ) );
        }

        return list1;
    }
}