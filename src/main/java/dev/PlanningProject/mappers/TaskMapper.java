package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductInPlaneMapper.class)
public interface TaskMapper {
    //todo переделать
    @Mapping(target = "groupId", source = "groupId")
    TaskEntity toTaskEntity(TaskDto taskDto);
    //todo переделать
    @Mapping(target = "groupId", source = "groupId")
    TaskDto toTaskDto(TaskEntity taskEntity);

}
