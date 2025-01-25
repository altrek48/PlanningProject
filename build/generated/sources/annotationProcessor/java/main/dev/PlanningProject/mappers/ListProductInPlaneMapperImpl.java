package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.repositories.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T12:12:02+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class ListProductInPlaneMapperImpl implements ListProductInPlaneMapper {

    @Autowired
    private ProductInPlaneMapper productInPlaneMapper;

    @Override
    public List<ProductInPlaneEntity> toListProductInPlaneEntity(List<ProductInPlaneDto> listProductInPlaneDto) {
        if ( listProductInPlaneDto == null ) {
            return null;
        }

        List<ProductInPlaneEntity> list = new ArrayList<ProductInPlaneEntity>( listProductInPlaneDto.size() );
        for ( ProductInPlaneDto productInPlaneDto : listProductInPlaneDto ) {
            list.add( productInPlaneDtoToProductInPlaneEntity( productInPlaneDto ) );
        }

        return list;
    }

    @Override
    public List<ProductInPlaneEntity> toListProductInPlaneEntity(List<ProductInPlaneDto> listProductInPlaneDto, ProductRepository productRepository) {
        if ( listProductInPlaneDto == null ) {
            return null;
        }

        List<ProductInPlaneEntity> list = new ArrayList<ProductInPlaneEntity>( listProductInPlaneDto.size() );
        for ( ProductInPlaneDto productInPlaneDto : listProductInPlaneDto ) {
            list.add( productInPlaneMapper.toProductInPlaneEntity( productInPlaneDto, productRepository ) );
        }

        return list;
    }

    @Override
    public List<ProductInPlaneDto> toListProductInPlaneDto(List<ProductInPlaneEntity> productInPlaneEntityList) {
        if ( productInPlaneEntityList == null ) {
            return null;
        }

        List<ProductInPlaneDto> list = new ArrayList<ProductInPlaneDto>( productInPlaneEntityList.size() );
        for ( ProductInPlaneEntity productInPlaneEntity : productInPlaneEntityList ) {
            list.add( productInPlaneMapper.toProductInPLaneDto( productInPlaneEntity ) );
        }

        return list;
    }

    protected ProductInPlaneEntity productInPlaneDtoToProductInPlaneEntity(ProductInPlaneDto productInPlaneDto) {
        if ( productInPlaneDto == null ) {
            return null;
        }

        ProductInPlaneEntity productInPlaneEntity = new ProductInPlaneEntity();

        productInPlaneEntity.setId( productInPlaneDto.getId() );
        productInPlaneEntity.setName( productInPlaneDto.getName() );
        productInPlaneEntity.setCompleteness( productInPlaneDto.getCompleteness() );
        productInPlaneEntity.setPrice( productInPlaneDto.getPrice() );

        return productInPlaneEntity;
    }
}
