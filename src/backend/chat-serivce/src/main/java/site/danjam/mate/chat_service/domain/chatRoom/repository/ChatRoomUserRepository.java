package site.danjam.mate.chat_service.domain.chatRoom.repository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoomUser;
import site.danjam.mate.chat_service.domain.chatRoom.repository.jpa.ChatRoomUserJpaRepository;

@Repository
@RequiredArgsConstructor
public class ChatRoomUserRepository {
    private ChatRoomUserJpaRepository chatRoomUserJpaRepository;

    public List<ChatRoomUser> saveAll(List<ChatRoomUser> chatRoomList) {
        return chatRoomUserJpaRepository.saveAll(chatRoomList);
    }

    public List<ChatRoom> findChatRoomByUserId(Long userId) {
        return chatRoomUserJpaRepository.findChatRoomByUserId(userId);
    }
}
