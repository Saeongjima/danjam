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
        return Optional.ofNullable(chatMessageJpaRepository.findLastMessageByChatRoomId(chatRoomId)).orElseThrow();
    }

    public Integer countUnreadMessages(Long chatRoomId, Long userId) {
        return chatMessageJpaRepository.countUnreadMessages(chatRoomId, userId);
    }

    public Optional<ChatMessage> findByChatRoomId(Long chatRoomId) {
        return Optional.ofNullable(chatMessageJpaRepository.findByChatRoomId(chatRoomId)).orElseThrow();
    }

    public void deleteByChatRoomId(Long chatRoomId) {
        chatMessageJpaRepository.deleteByChatRoomId(chatRoomId);
    }
}
