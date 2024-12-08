package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = GroupMapper.class)
public interface ListGroupMapper {

    List<GroupDto> toGroupDto(List<GroupEntity> groupEntities);

    List<GroupEntity> toGroupEntity(List<GroupDto> groupDtos);

}
