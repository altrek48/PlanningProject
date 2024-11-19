package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductInPlaneMapper.class)
public interface TaskMapper {

    @Mapping(target = "purchases", ignore = true)
    @Mapping(target = "group.id", source = "group_id")
    TaskEntity toTaskEntity(TaskDto taskDto);

    @Mapping(target = "purchases_ids", ignore = true)
    @Mapping(target = "group_id", source = "group.id")
    TaskDto toTaskDto(TaskEntity taskEntity);

}
