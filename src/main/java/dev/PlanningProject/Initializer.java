package dev.PlanningProject;

import dev.PlanningProject.dtos.*;
import dev.PlanningProject.dtos.auth.SignInRequest;
import dev.PlanningProject.entities.*;
import dev.PlanningProject.repositories.GroupRepository;
import dev.PlanningProject.repositories.PurchaseRepository;
import dev.PlanningProject.repositories.TaskRepository;
import dev.PlanningProject.services.GroupService;
import dev.PlanningProject.services.PurchaseService;
import dev.PlanningProject.services.TaskService;
import dev.PlanningProject.services.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Initializer {

    private final JwtUserDetailsService userDetailsService;
    private final GroupService groupService;
    private final TaskService taskService;
    private final PurchaseService purchaseService;

    public void initial1() {

        userDetailsService.createUser(SignInRequest.builder()
                .username("rolik222")
                .password("1234")
                .build());

        groupService.createGroup(GroupDto.builder()
                .name("Семья")
                .build(),
                "rolik222"
        );

        userDetailsService.createUser(SignInRequest.builder()
                .username("bobik333")
                .password("1234")
                .build());

        groupService.createGroup(GroupDto.builder()
                        .name("Коллеги")
                        .build(),
                "bobik333"
        );

        TaskDto taskDto = TaskDto.builder()
                .name("На день рождения")
                .comment("Купить подарке Тане, Ане, Паше")
                .products(Arrays.asList(
                        ProductInPlaneDto.builder().name("Кукла").completeness(false).build(),
                        ProductInPlaneDto.builder().name("Машинка").completeness(false).build(),
                        ProductInPlaneDto.builder().name("Незамерзайка").completeness(false).build()
                ))
                .build();

        taskService.createTask(taskDto, 1L, "rolik222");


        TaskDto taskDto2 = TaskDto.builder()
                .name("В дорогу на море")
                .comment("Купить не позднее 12.01.25")
                .products(Arrays.asList(
                        ProductInPlaneDto.builder().name("Сникерсы").completeness(false).build(),
                        ProductInPlaneDto.builder().name("Воду").completeness(false).build(),
                        ProductInPlaneDto.builder().name("Парацетамол").completeness(false).build()
                ))
                .build();

        taskService.createTask(taskDto2, 1L, "rolik222");

        PurchaseDto purchaseDto1 = PurchaseDto.builder()
                .storeName("Pyterochka")
                .amount(BigDecimal.valueOf(42242))
                .products(Arrays.asList(
                        ProductDto.builder().name("Gorshok").price(BigDecimal.valueOf(2345)).quantity(1L).build()
                ))
                .build();

        purchaseService.createPurchase(purchaseDto1, 1L, "rolik222");
    }


}
