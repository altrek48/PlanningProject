package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.PurchaseDto;
import dev.PlanningProject.entities.GroupEntity;
import dev.PlanningProject.entities.PurchaseEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-08T12:52:32+0300",
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
        purchaseEntity.setAmount( purchaseDto.getAmount() );
        purchaseEntity.setProducts( listProductMapper.toListProductEntity( purchaseDto.getProducts() ) );

        return purchaseEntity;
    }

    @Override
    public PurchaseDto toPurchaseDto(PurchaseEntity purchaseEntity) {
        if ( purchaseEntity == null ) {
            return null;
        }

        PurchaseDto purchaseDto = new PurchaseDto();

        purchaseDto.setGroupId( purchaseEntity.getGroupId() );
        purchaseDto.setId( purchaseEntity.getId() );
        purchaseDto.setStoreName( purchaseEntity.getStoreName() );
        purchaseDto.setAmount( purchaseEntity.getAmount() );
        purchaseDto.setProducts( listProductMapper.toListProductDto( purchaseEntity.getProducts() ) );

        return purchaseDto;
    }

    @Override
    public PurchaseDto toPurchaseDtoWithPlane(PurchaseEntity purchaseEntity) {
        if ( purchaseEntity == null ) {
            return null;
        }

        PurchaseDto purchaseDto = new PurchaseDto();

        purchaseDto.setGroupId( purchaseEntityGroupId( purchaseEntity ) );
        purchaseDto.setId( purchaseEntity.getId() );
        purchaseDto.setStoreName( purchaseEntity.getStoreName() );
        purchaseDto.setAmount( purchaseEntity.getAmount() );
        purchaseDto.setProducts( listProductMapper.toListProductDto( purchaseEntity.getProducts() ) );

        return purchaseDto;
    }

    private Long purchaseEntityGroupId(PurchaseEntity purchaseEntity) {
        GroupEntity group = purchaseEntity.getGroup();
        if ( group == null ) {
            return null;
        }
        return group.getId();
    }
}
