package site.danjam.mate.mate_service.romm_mate.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RoomMateProfileRepository {

    private final RoomMateProfileJpaRepository roomMateProfileJpaRepository;

}
