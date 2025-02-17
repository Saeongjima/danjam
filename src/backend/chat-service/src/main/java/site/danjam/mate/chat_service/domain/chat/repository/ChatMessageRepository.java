package site.danjam.mate.chat_service.domain.chat.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chat.domaiin.ChatMessage;
import site.danjam.mate.chat_service.domain.chat.repository.jpa.ChatMessageJpaRepository;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepository {
    private final ChatMessageJpaRepository chatMessageJpaRepository;

    public Optional<ChatMessage> findLastMessageByChatRoomId(Long chatRoomId) {
        return chatMessageJpaRepository.findLastMessageByChatRoomId(chatRoomId);
    }

    public Integer countUnreadMessages(Long chatRoomId, Long userId) {
        return chatMessageJpaRepository.countUnreadMessages(chatRoomId, userId);
    }

}
