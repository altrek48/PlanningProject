package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import dev.PlanningProject.entities.UserCredentialsEntity;
import dev.PlanningProject.entities.UserEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-02T20:04:58+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class PurchaseMapperImpl implements PurchaseMapper {

    @Autowired
    private ListProductMapper listProductMapper;

    @Override
    public PurchaseEntity toPurchaseEntity(PurchaseDto purchaseDto) {
        if ( purchaseDto == null ) {
            return null;
        }

        PurchaseEntity purchaseEntity = new PurchaseEntity();

        purchaseEntity.setGroupId( purchaseDto.getGroupId() );
        purchaseEntity.setId( purchaseDto.getId() );
        purchaseEntity.setStoreName( purchaseDto.getStoreName() );
        purchaseEntity.setDate( purchaseDto.getDate() );
        purchaseEntity.setAmount( purchaseDto.getAmount() );
        purchaseEntity.setProducts( listProductMapper.toListProductEntity( purchaseDto.getProducts() ) );

        return purchaseEntity;
    }

    @Override
    public PurchaseDto toPurchaseDto(PurchaseEntity purchaseEntity) {
        if ( purchaseEntity == null ) {
            return null;
        }

        PurchaseDto.PurchaseDtoBuilder purchaseDto = PurchaseDto.builder();

        purchaseDto.userPayer( purchaseEntityUserPayerLinkedUserCredentialsUsername( purchaseEntity ) );
        purchaseDto.groupId( purchaseEntity.getGroupId() );
        purchaseDto.id( purchaseEntity.getId() );
        purchaseDto.storeName( purchaseEntity.getStoreName() );
        purchaseDto.date( purchaseEntity.getDate() );
        purchaseDto.amount( purchaseEntity.getAmount() );
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
        UserCredentialsEntity linkedUserCredentials = userPayer.getLinkedUserCredentials();
        if ( linkedUserCredentials == null ) {
            return null;
        }
        return linkedUserCredentials.getUsername();
    }

    private Long purchaseEntityGroupId(PurchaseEntity purchaseEntity) {
        GroupEntity group = purchaseEntity.getGroup();
        if ( group == null ) {
            return null;
        }
        return group.getId();
    }
}
