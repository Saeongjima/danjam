package site.danjam.mate.chat_service.domain.chat.repository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chat.domaiin.ChatMessage;
import site.danjam.mate.chat_service.domain.chat.exception.NotFoundMessageException;
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

    public ChatMessage findById(Long id) {
        return chatMessageJpaRepository.findById(id).orElseThrow(NotFoundMessageException::new);
    }

    public void deleteByChatRoomId(Long chatRoomId) {
        chatMessageJpaRepository.deleteByChatRoomId(chatRoomId);
    }

    public ChatMessage save(ChatMessage chatMessage) {
        return chatMessageJpaRepository.save(chatMessage);
    }

    public List<ChatMessage> findByChatRoomIdAndUnreadUsersContains(Long chatRoomId, Long userId) {
        return chatMessageJpaRepository.findByChatRoomIdAndUnreadUsersContains(chatRoomId, userId);
    }

    public List<ChatMessage> findByChatRoomId(Long chatRoomId) {
        return chatMessageJpaRepository.findByChatRoomId(chatRoomId);
    }
}
