package site.danjam.mate.mate_service.romm_mate.repository;

import java.util.List;
import site.danjam.mate.mate_service.romm_mate.domain.RoomMateProfile;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateFilterInputDTO;
import site.danjam.mate.mate_service.romm_mate.dto.RoomMateProfileInputDTO;

public interface RoomMateProfieQueryDSLRepository {

    public List<RoomMateProfile> filterRoomMateProfiles(RoomMateFilterInputDTO roomMateFilterInputDTO);
}
