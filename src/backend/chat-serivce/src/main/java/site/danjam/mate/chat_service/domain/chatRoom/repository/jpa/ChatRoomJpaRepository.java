package site.danjam.mate.chat_service.domain.chatRoom.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;

@Repository
public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {
}
