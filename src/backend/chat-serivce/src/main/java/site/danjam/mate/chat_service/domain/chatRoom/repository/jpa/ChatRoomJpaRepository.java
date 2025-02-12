package site.danjam.mate.chat_service.domain.chatRoom.repository.jpa;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;

@Repository
public interface ChatRoomJpaRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findById(Long chatRoomId);

    @Query("select c from ChatRoom c order by c.createdDateTime desc")
    List<ChatRoom> findAllChatRoom();

    @Query("select c from ChatRoom c " +
            "join c.chatRoomUsers  cu1 on cu1.userId = :userId " +
            "join c.chatRoomUsers cu2 on cu2.userId = :friendId ")
    Optional<ChatRoom> findExistingPersonalChatRoom(@Param("userId") Long userid, @Param("friendId") Long friendId);
}
