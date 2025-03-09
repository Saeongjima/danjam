package site.danjam.mate.user_service.domain.user.repository;

import java.util.List;
import site.danjam.mate.user_service.domain.user.dto.UserFilterInputDTO;
import site.danjam.mate.user_service.domain.user.dto.UserFilterOutputDTO;

public interface UserQueryDSLRepository {
    public List<UserFilterOutputDTO> filterUsers(UserFilterInputDTO filter);
}
