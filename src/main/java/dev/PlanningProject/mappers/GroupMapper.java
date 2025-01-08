package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {

    @Mapping(target = "userCreator", source = "user")
    @Mapping(target = "id", ignore = true) //id генерируется автоматически(без этого маппинга вылетает ошибка)
    GroupEntity toGroupEntity(GroupDto groupDto, UserEntity user);

    GroupDto toGroupDto(GroupEntity groupEntity);

}
