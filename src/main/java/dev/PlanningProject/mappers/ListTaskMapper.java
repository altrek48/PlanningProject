package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = TaskMapper.class)
public interface ListTaskMapper {

    List<TaskDto> toListTaskDto(List<TaskEntity> taskEntityList);

    List<TaskShortDto> toListTaskShortDto(List<TaskEntity> taskEntityList);

}
