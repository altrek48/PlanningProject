package dev.PlanningProject.services;

import dev.PlanningProject.entities.GroupEntity;
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

    public GroupEntity createGroup(GroupEntity group) {
        return groupRepository.save(group);
    }

    public Long deleteGroupById(Long id) {
        GroupEntity group = groupRepository.getReferenceById(id);
        if (group.getTasks() != null) {
            taskService.deleteAllTasksInGroup(group);
        }
        if (group.getPurchases() != null) {
            purchaseService.deleteAllPurchasesInGroup(group);
        }
        groupRepository.deleteById(id);
        return id;
    }

}
