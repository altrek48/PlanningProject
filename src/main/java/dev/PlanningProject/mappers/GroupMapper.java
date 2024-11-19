package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    GroupEntity toGroupEntity(GroupDto groupDto);

    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "purchases", ignore = true)
    GroupDto toGroupDto(GroupEntity groupEntity);

}
