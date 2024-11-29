package dev.PlanningProject.services;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.mappers.GroupMapper;
import dev.PlanningProject.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupDto createGroup(GroupDto group) {
        GroupEntity newGroup = groupMapper.toGroupEntity(group);
        groupRepository.save(newGroup);
        return groupMapper.toGroupDto(newGroup);
    }

    public Long deleteGroupById(Long id) {
        groupRepository.deleteById(id);
        return id;
    }

}
