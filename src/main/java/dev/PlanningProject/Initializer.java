package dev.PlanningProject;

import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class Initializer {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    PurchaseRepository purchaseRepository;

    public void initial() {

    GroupEntity group1 = new GroupEntity("family");

    purchaseRepository.save(new PurchaseEntity(null, "Pyaterochka", new Date(),
        new BigDecimal(423), group1 ));

    }


}
