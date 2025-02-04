package site.danjam.mate.chat_service.domain.chatRoom.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoomUser;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoomUserPK;

@Repository
public interface ChatRoomUserJpaRepository extends JpaRepository<ChatRoomUser, ChatRoomUserPK> {
}
