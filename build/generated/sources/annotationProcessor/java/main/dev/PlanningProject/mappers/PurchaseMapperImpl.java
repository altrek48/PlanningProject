package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.entities.CredentialsEntity;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.TaskEntity;
import dev.PlanningProject.entities.UserEntity;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-12T02:18:57+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class PurchaseMapperImpl implements PurchaseMapper {

    @Autowired
    private ListProductMapper listProductMapper;

    @Override
    public PurchaseEntity toPurchaseEntity(PurchaseDto purchaseDto, Long groupId, LocalDateTime now, UserEntity userPayer) {
        if ( purchaseDto == null && groupId == null && now == null && userPayer == null ) {
            return null;
        }

        PurchaseEntity purchaseEntity = new PurchaseEntity();

        if ( purchaseDto != null ) {
            purchaseEntity.setStoreName( purchaseDto.getStoreName() );
            purchaseEntity.setAmount( purchaseDto.getAmount() );
            purchaseEntity.setProducts( listProductMapper.toListProductEntity( purchaseDto.getProducts() ) );
        }
        purchaseEntity.setGroupId( groupId );
        purchaseEntity.setDate( now );
        purchaseEntity.setUserPayer( userPayer );

        return purchaseEntity;
    }

    @Override
    public PurchaseDto toPurchaseDto(PurchaseEntity purchaseEntity) {
        if ( purchaseEntity == null ) {
            return null;
        }

        PurchaseDto.PurchaseDtoBuilder purchaseDto = PurchaseDto.builder();

        purchaseDto.userPayer( purchaseEntityUserPayerLinkedUserCredentialsUsername( purchaseEntity ) );
        purchaseDto.taskId( purchaseEntityLinkedTaskId( purchaseEntity ) );
        purchaseDto.id( purchaseEntity.getId() );
        purchaseDto.storeName( purchaseEntity.getStoreName() );
        purchaseDto.date( purchaseEntity.getDate() );
        purchaseDto.amount( purchaseEntity.getAmount() );
        purchaseDto.groupId( purchaseEntity.getGroupId() );
        purchaseDto.products( listProductMapper.toListProductDto( purchaseEntity.getProducts() ) );

        return purchaseDto.build();
    }

    @Override
    public PurchaseDto toPurchaseDtoWithPlane(PurchaseEntity purchaseEntity) {
        if ( purchaseEntity == null ) {
            return null;
        }

        PurchaseDto.PurchaseDtoBuilder purchaseDto = PurchaseDto.builder();

        purchaseDto.groupId( purchaseEntityGroupId( purchaseEntity ) );
        purchaseDto.id( purchaseEntity.getId() );
        purchaseDto.storeName( purchaseEntity.getStoreName() );
        purchaseDto.date( purchaseEntity.getDate() );
        purchaseDto.amount( purchaseEntity.getAmount() );
        purchaseDto.products( listProductMapper.toListProductDto( purchaseEntity.getProducts() ) );

        return purchaseDto.build();
    }

    @Override
    public PurchaseShortDto toPurchaseShortDto(PurchaseEntity purchaseEntity) {
        if ( purchaseEntity == null ) {
            return null;
        }

        PurchaseShortDto purchaseShortDto = new PurchaseShortDto();

        purchaseShortDto.setUserPayer( purchaseEntityUserPayerLinkedUserCredentialsUsername( purchaseEntity ) );
        purchaseShortDto.setId( purchaseEntity.getId() );
        purchaseShortDto.setStoreName( purchaseEntity.getStoreName() );
        purchaseShortDto.setDate( purchaseEntity.getDate() );
        purchaseShortDto.setAmount( purchaseEntity.getAmount() );
        purchaseShortDto.setGroupId( purchaseEntity.getGroupId() );

        return purchaseShortDto;
    }

    private String purchaseEntityUserPayerLinkedUserCredentialsUsername(PurchaseEntity purchaseEntity) {
        UserEntity userPayer = purchaseEntity.getUserPayer();
        if ( userPayer == null ) {
            return null;
        }
        CredentialsEntity linkedUserCredentials = userPayer.getLinkedUserCredentials();
        if ( linkedUserCredentials == null ) {
            return null;
        }
        return linkedUserCredentials.getUsername();
    }

    private Long purchaseEntityLinkedTaskId(PurchaseEntity purchaseEntity) {
        TaskEntity linkedTask = purchaseEntity.getLinkedTask();
        if ( linkedTask == null ) {
            return null;
        }
        return linkedTask.getId();
    }

    private Long purchaseEntityGroupId(PurchaseEntity purchaseEntity) {
        GroupEntity group = purchaseEntity.getGroup();
        if ( group == null ) {
            return null;
        }
        return group.getId();
    }
}
