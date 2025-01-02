package dev.PlanningProject.mappers;

import dev.PlanningProject.dtos.GroupDto;
import dev.PlanningProject.entities.GroupEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-02T04:09:47+0300",
    comments = "version: 1.6.2, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.10.2.jar, environment: Java 21.0.5 (Oracle Corporation)"
)
@Component
public class ListGroupMapperImpl implements ListGroupMapper {

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public List<GroupDto> toGroupDto(List<GroupEntity> groupEntities) {
        if ( groupEntities == null ) {
            return null;
        }

        List<GroupDto> list = new ArrayList<GroupDto>( groupEntities.size() );
        for ( GroupEntity groupEntity : groupEntities ) {
            list.add( groupMapper.toGroupDto( groupEntity ) );
        }

        return list;
    }

    @Override
    public List<GroupEntity> toGroupEntity(List<GroupDto> groupDtos) {
        if ( groupDtos == null ) {
            return null;
        }

        List<GroupEntity> list = new ArrayList<GroupEntity>( groupDtos.size() );
        for ( GroupDto groupDto : groupDtos ) {
            list.add( groupMapper.toGroupEntity( groupDto ) );
        }

        return list;
    }
}
