package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductInPlaneMapper.class)
public interface TaskMapper {
    //todo переделать
    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "group.id", source = "groupId")
    TaskEntity toTaskEntity(TaskDto taskDto);
    //todo переделать
    @Mapping(target = "purchasesIds", ignore = true)
    @Mapping(target = "groupId", source = "group.id")
    TaskDto toTaskDto(TaskEntity taskEntity);

}
