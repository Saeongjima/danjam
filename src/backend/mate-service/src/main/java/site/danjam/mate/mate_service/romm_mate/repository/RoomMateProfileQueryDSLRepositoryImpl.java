package site.danjam.mate.mate_service.romm_mate.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.mate_service.romm_mate.domain.RoomMateProfile;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateFilterInputDTO;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateProfileInputDTO;


@Repository
@RequiredArgsConstructor
public class RoomMateProfileQueryDSLRepositoryImpl implements RoomMateProfieQueryDSLRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RoomMateProfile> filterRoomMateProfiles(RoomMateFilterInputDTO roomMateFilterInputDTO) {

        return List.of();
    }
}
