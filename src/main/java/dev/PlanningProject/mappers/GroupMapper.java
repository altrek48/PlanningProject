package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface GroupMapper {

    GroupEntity toGroupEntity(GroupDto groupDto);

    GroupDto toGroupDto(GroupEntity groupEntity);

}
