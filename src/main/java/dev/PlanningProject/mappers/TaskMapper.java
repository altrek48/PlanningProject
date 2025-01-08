package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.TaskDto;
import dev.PlanningProject.dtos.TaskShortDto;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = ListProductInPlaneMapper.class)
public interface TaskMapper {
    //todo переделать
    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "userCreator", source = "userCreator")
    @Mapping(target = "id", ignore = true) //id генерируется автоматически(без этого маппинга вылетает ошибка)
    //@Mapping(target = "purchases.id", source = "purchasesIds")
    TaskEntity toTaskEntity(TaskDto taskDto, Long groupId, UserEntity userCreator);

    @Mapping(target = "userCreator", ignore = true)
        //@Mapping(target = "purchases.id", source = "purchasesIds")
    TaskEntity toTaskEntity(TaskDto taskDto);

    //todo переделать
    @Mapping(target = "userCreator", source = "userCreator.linkedUserCredentials.username")
    TaskDto toTaskDto(TaskEntity taskEntity);

    TaskShortDto toTaskShortDto(TaskEntity taskEntity);

}
