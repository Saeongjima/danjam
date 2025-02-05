package site.danjam.mate.chat_service.domain.chatRoom.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.domain.chatRoom.repository.jpa.ChatRoomJpaRepository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final ChatRoomJpaRepository chatRoomJpaRepository;

    public Optional<ChatRoom> findById(Long chatRoomId) {
        return chatRoomJpaRepository.findById(chatRoomId);
    }

    public List<ChatRoom> findAllChatRoom() {
        return chatRoomJpaRepository.findAllChatRoom();
    }

}
