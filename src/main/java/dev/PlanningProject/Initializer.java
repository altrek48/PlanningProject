package dev.PlanningProject;

import dev.PlanningProject.dtos.*;
import dev.PlanningProject.dtos.auth.SignInRequest;
import dev.PlanningProject.services.GroupService;
import dev.PlanningProject.services.PurchaseService;
import dev.PlanningProject.services.TaskService;
import dev.PlanningProject.services.auth.JwtUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

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
                .password("12345")
                .build());

        groupService.createGroup(GroupDto.builder()
                .name("Family")
                .build(),
                "rolik222"
        );

        userDetailsService.createUser(SignInRequest.builder()
                .username("bobik333")
                .password("12345")
                .build());

        groupService.createGroup(GroupDto.builder()
                .name("Colleagues")
                .build(),
                "bobik333"
        );

        TaskDto taskDto = TaskDto.builder()
                .name("To happy birthday")
                .comment("Buy a gift for Tanya, Anya, Pasha")
                .products(Arrays.asList(
                        ProductInPlaneDto.builder().name("Doll").completeness(false).build(),
                        ProductInPlaneDto.builder().name("Car").completeness(false).build(),
                        ProductInPlaneDto.builder().name("Anti-freeze").completeness(false).build()
                ))
                .build();

        taskService.createTask(taskDto, 1L, "rolik222");


        TaskDto taskDto2 = TaskDto.builder()
                .name("On the way to the sea")
                .comment("Buy no later than 01/12/25")
                .products(Arrays.asList(
                        ProductInPlaneDto.builder().name("Snickers").completeness(false).build(),
                        ProductInPlaneDto.builder().name("Water").completeness(false).build(),
                        ProductInPlaneDto.builder().name("Paracetamol").completeness(false).build()
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



//        PurchaseDto purchaseDto2 = PurchaseDto.builder()
//                .storeName("Magnit")
//                .products(Arrays.asList(
//                        ProductDto.builder().name("Кукла").price(BigDecimal.valueOf(111)).quantity(1L).build()
////                        ProductDto.builder().name("Машинка").price(BigDecimal.valueOf(444)).quantity(1L).build()
//                ))
//                .build();
//
//        purchaseService.createPurchaseInTask(purchaseDto2, 1L, 1L, "rolik222");
    }

}
