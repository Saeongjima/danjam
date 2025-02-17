package site.danjam.mate.chat_service.domain.chatRoom.repository.jpa;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoomUser;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoomUserPK;

@Repository
public interface ChatRoomUserJpaRepository extends JpaRepository<ChatRoomUser, ChatRoomUserPK> {

    @Query("select cu.chatRoom from ChatRoomUser cu where cu.userId = :userId")
    List<ChatRoom> findChatRoomByUserId(@Param("userId") Long userId);

    ChatRoomUser findByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    long countByChatRoomId(Long chatRoomId);
}
