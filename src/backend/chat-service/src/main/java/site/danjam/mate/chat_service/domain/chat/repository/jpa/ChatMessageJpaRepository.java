package site.danjam.mate.chat_service.domain.chat.repository.jpa;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chat.domaiin.ChatMessage;

@Repository
public interface ChatMessageJpaRepository extends JpaRepository<ChatMessage, Long> {

    @Query("select cm from ChatMessage cm "
            + "where cm.chatRoomId = :chatRoomId order by cm.id desc limit 1")
    Optional<ChatMessage> findLastMessageByChatRoomId(@Param("chatRoomId") Long chatRoomId);


    // 특정 채팅방에서 유저가 읽지 않은 메시지를 조회
    @Query(value = "SELECT COUNT(*) FROM chat_message " +
            "WHERE chat_room_id = :chatRoomId " +
            "AND JSON_CONTAINS(unread_users, :userId, '$')", nativeQuery = true)
    Integer countUnreadMessages(@Param("chatRoomId") Long chatRoomId, @Param("userId") Long userId);

    Optional<ChatMessage> findByChatRoomId(Long chatRoomId);

    void deleteByChatRoomId(Long chatRoomId);
}
