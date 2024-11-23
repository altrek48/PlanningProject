package dev.PlanningProject.services;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.mappers.GroupMapper;
import dev.PlanningProject.repositories.GroupRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    GroupMapper groupMapper;

    public GroupDto createGroup(GroupDto group) {
        GroupEntity newGroup = groupMapper.toGroupEntity(group);
        groupRepository.save(newGroup);
        return groupMapper.toGroupDto(newGroup);
    }

    public Long deleteGroupById(Long id) {
        GroupEntity group = groupRepository.getReferenceById(id);
        if (group.getPurchases() != null) {
            purchaseService.deleteAllPurchasesInGroup(group);
        }
        if (group.getTasks() != null) {
            taskService.deleteAllTasksInGroup(group);
        }
        groupRepository.deleteById(id);
        return id;
    }

}
