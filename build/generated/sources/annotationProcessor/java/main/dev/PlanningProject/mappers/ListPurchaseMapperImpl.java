package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.PurchaseShortDto;
import dev.PlanningProject.entities.PurchaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-18T20:59:52+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.12.jar, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class ListPurchaseMapperImpl implements ListPurchaseMapper {

    @Autowired
    private PurchaseMapper purchaseMapper;

    @Override
    public List<PurchaseShortDto> toListPurchaseShortDto(List<PurchaseEntity> purchases) {
        if ( purchases == null ) {
            return null;
        }

        List<PurchaseShortDto> list = new ArrayList<PurchaseShortDto>( purchases.size() );
        for ( PurchaseEntity purchaseEntity : purchases ) {
            list.add( purchaseMapper.toPurchaseShortDto( purchaseEntity ) );
        }

        return list;
    }
}
