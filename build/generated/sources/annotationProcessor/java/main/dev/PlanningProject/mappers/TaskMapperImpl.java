package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.TaskEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-23T23:44:13+0300",
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

        taskEntity.setGroup( taskDtoToGroupEntity( taskDto ) );
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

        TaskDto taskDto = new TaskDto();

        taskDto.setGroupId( taskEntityGroupId( taskEntity ) );
        taskDto.setId( taskEntity.getId() );
        taskDto.setName( taskEntity.getName() );
        taskDto.setComment( taskEntity.getComment() );
        taskDto.setAmount( taskEntity.getAmount() );
        taskDto.setProducts( listProductInPlaneMapper.toListProductInPlaneDto( taskEntity.getProducts() ) );

        return taskDto;
    }

    protected GroupEntity taskDtoToGroupEntity(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        GroupEntity groupEntity = new GroupEntity();

        groupEntity.setId( taskDto.getGroupId() );

        return groupEntity;
    }

    private Long taskEntityGroupId(TaskEntity taskEntity) {
        GroupEntity group = taskEntity.getGroup();
        if ( group == null ) {
            return null;
        }
        return group.getId();
    }
}
