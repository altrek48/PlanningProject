package dev.PlanningProject.services;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.mappers.GroupMapper;
import dev.PlanningProject.mappers.ListGroupMapper;
import dev.PlanningProject.repositories.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final ListGroupMapper listGroupMapper;

    public GroupDto createGroup(GroupDto group) {
        GroupEntity newGroup = groupMapper.toGroupEntity(group);
        groupRepository.save(newGroup);
        return groupMapper.toGroupDto(newGroup);
    }

    public Long deleteGroupById(Long id) {
        groupRepository.deleteById(id);
        return id;
    }

    public List<GroupDto> getAllGroups() {
        List<GroupEntity> groups = groupRepository.findAllShorted();
        return listGroupMapper.toGroupDto(groups);
    }

}
