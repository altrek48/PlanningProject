package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductInPlaneMapper.class)
public interface TaskMapper {
    //todo переделать
    @Mapping(target = "userCreator", ignore = true)
    @Mapping(target = "groupId", source = "groupId")
    //@Mapping(target = "purchases.id", source = "purchasesIds")
    TaskEntity toTaskEntity(TaskDto taskDto, Long groupId);

    @Mapping(target = "userCreator", ignore = true)
        //@Mapping(target = "purchases.id", source = "purchasesIds")
    TaskEntity toTaskEntity(TaskDto taskDto);

    //todo переделать
    @Mapping(target = "userCreator", source = "userCreator.linkedUserCredentials.username")
    TaskDto toTaskDto(TaskEntity taskEntity);

    TaskShortDto toTaskShortDto(TaskEntity taskEntity);

}
