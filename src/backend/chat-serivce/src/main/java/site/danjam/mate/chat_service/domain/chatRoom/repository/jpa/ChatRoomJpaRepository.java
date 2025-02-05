package site.danjam.mate.chat_service.domain.chatRoom.repository.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;

@Repository
public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findById(Long chatRoomId);

    @Query("select c from ChatRoom c order by c.createdDateTime desc")
    List<ChatRoom> findAllChatRoom();
}
