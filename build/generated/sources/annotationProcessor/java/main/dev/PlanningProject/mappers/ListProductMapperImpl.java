package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductDto;
import dev.PlanningProject.entities.ProductEntity;
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
public class ListProductMapperImpl implements ListProductMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductEntity> toListProductEntity(List<ProductDto> productDtoList) {
        if ( productDtoList == null ) {
            return null;
        }

        List<ProductEntity> list = new ArrayList<ProductEntity>( productDtoList.size() );
        for ( ProductDto productDto : productDtoList ) {
            list.add( productMapper.toProductEntity( productDto ) );
        }

        return list;
    }
}
