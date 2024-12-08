package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.ProductInPlaneDto;
import dev.PlanningProject.entities.ProductInPlaneEntity;
import dev.PlanningProject.entities.TaskEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-08T12:52:32+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ProductInPlaneMapperImpl implements ProductInPlaneMapper {

    @Override
    public ProductInPlaneEntity toProductInPlaneEntity(ProductInPlaneDto productInPlaneDto) {
        if ( productInPlaneDto == null ) {
            return null;
        }

        ProductInPlaneEntity productInPlaneEntity = new ProductInPlaneEntity();

        productInPlaneEntity.setTask( productInPlaneDtoToTaskEntity( productInPlaneDto ) );
        productInPlaneEntity.setId( productInPlaneDto.getId() );
        productInPlaneEntity.setName( productInPlaneDto.getName() );
        productInPlaneEntity.setCompleteness( productInPlaneDto.getCompleteness() );
        productInPlaneEntity.setPrice( productInPlaneDto.getPrice() );

        return productInPlaneEntity;
    }

    @Override
    public ProductInPlaneDto toProductInPLaneDto(ProductInPlaneEntity productInPlaneEntity) {
        if ( productInPlaneEntity == null ) {
            return null;
        }

        ProductInPlaneDto productInPlaneDto = new ProductInPlaneDto();

        productInPlaneDto.setTaskId( productInPlaneEntityTaskId( productInPlaneEntity ) );
        productInPlaneDto.setId( productInPlaneEntity.getId() );
        productInPlaneDto.setName( productInPlaneEntity.getName() );
        productInPlaneDto.setCompleteness( productInPlaneEntity.getCompleteness() );
        productInPlaneDto.setPrice( productInPlaneEntity.getPrice() );

        return productInPlaneDto;
    }

    protected TaskEntity productInPlaneDtoToTaskEntity(ProductInPlaneDto productInPlaneDto) {
        if ( productInPlaneDto == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setId( productInPlaneDto.getTaskId() );

        return taskEntity;
    }

    private Long productInPlaneEntityTaskId(ProductInPlaneEntity productInPlaneEntity) {
        TaskEntity task = productInPlaneEntity.getTask();
        if ( task == null ) {
            return null;
        }
        return task.getId();
    }
}
