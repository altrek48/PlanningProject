package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.CredentialsEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.entities.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-18T20:59:52+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Autowired
    private ListProductInPlaneMapper listProductInPlaneMapper;

    @Override
    public TaskEntity toTaskEntity(TaskDto taskDto, Long groupId, UserEntity userCreator) {
        if ( taskDto == null && groupId == null && userCreator == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        if ( taskDto != null ) {
            taskEntity.setName( taskDto.getName() );
            taskEntity.setComment( taskDto.getComment() );
            taskEntity.setAmount( taskDto.getAmount() );
            taskEntity.setCompleteness( taskDto.getCompleteness() );
            taskEntity.setProducts( listProductInPlaneMapper.toListProductInPlaneEntity( taskDto.getProducts() ) );
        }
        taskEntity.setGroupId( groupId );
        taskEntity.setUserCreator( userCreator );

        return taskEntity;
    }

    @Override
    public TaskEntity toTaskEntity(TaskDto taskDto) {
        if ( taskDto == null ) {
            return null;
        }

        TaskEntity taskEntity = new TaskEntity();

        taskEntity.setId( taskDto.getId() );
        taskEntity.setName( taskDto.getName() );
        taskEntity.setComment( taskDto.getComment() );
        taskEntity.setAmount( taskDto.getAmount() );
        taskEntity.setCompleteness( taskDto.getCompleteness() );
        taskEntity.setProducts( listProductInPlaneMapper.toListProductInPlaneEntity( taskDto.getProducts() ) );
        taskEntity.setGroupId( taskDto.getGroupId() );

        return taskEntity;
    }

    @Override
    public TaskDto toTaskDto(TaskEntity taskEntity) {
        if ( taskEntity == null ) {
            return null;
        }

        TaskDto.TaskDtoBuilder taskDto = TaskDto.builder();

        taskDto.userCreator( taskEntityUserCreatorLinkedUserCredentialsUsername( taskEntity ) );
        taskDto.id( taskEntity.getId() );
        taskDto.name( taskEntity.getName() );
        taskDto.comment( taskEntity.getComment() );
        taskDto.amount( taskEntity.getAmount() );
        taskDto.completeness( taskEntity.getCompleteness() );
        taskDto.products( listProductInPlaneMapper.toListProductInPlaneDto( taskEntity.getProducts() ) );
        taskDto.groupId( taskEntity.getGroupId() );

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

    private String taskEntityUserCreatorLinkedUserCredentialsUsername(TaskEntity taskEntity) {
        UserEntity userCreator = taskEntity.getUserCreator();
        if ( userCreator == null ) {
            return null;
        }
        CredentialsEntity linkedUserCredentials = userCreator.getLinkedUserCredentials();
        if ( linkedUserCredentials == null ) {
            return null;
        }
        return linkedUserCredentials.getUsername();
    }
}
