package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.TaskEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-29T18:26:56+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Autowired
    private ListProductInPlaneMapper listProductInPlaneMapper;

    @Override
    public TaskEntity toTaskEntity(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setGroupId( taskDto.getGroupId() );
        taskEntity.setId( taskDto.getId() );
        taskEntity.setName( taskDto.getName() );
        taskEntity.setComment( taskDto.getComment() );
        taskEntity.setAmount( taskDto.getAmount() );
        taskEntity.setProducts( listProductInPlaneMapper.toListProductInPlaneEntity( taskDto.getProducts() ) );

        return taskEntity;
    }

    @Override
    public TaskDto toTaskDto(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskDto.TaskDtoBuilder taskDto = TaskDto.builder();

        taskDto.groupId( taskEntity.getGroupId() );
        taskDto.id( taskEntity.getId() );
        taskDto.name( taskEntity.getName() );
        taskDto.comment( taskEntity.getComment() );
        taskDto.amount( taskEntity.getAmount() );
        taskDto.products( listProductInPlaneMapper.toListProductInPlaneDto( taskEntity.getProducts() ) );

        return taskDto.build();
    }

    @Override
    public TaskShortDto toTaskShortDto(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskShortDto taskShortDto = new TaskShortDto();

        taskShortDto.setId( taskEntity.getId() );
        taskShortDto.setName( taskEntity.getName() );
        taskShortDto.setComment( taskEntity.getComment() );
        taskShortDto.setAmount( taskEntity.getAmount() );
        taskShortDto.setCompleteness( taskEntity.getCompleteness() );

        return taskShortDto;
    }
}
