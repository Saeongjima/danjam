package site.danjam.mate.chat_service.domain.chat.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.danjam.mate.chat_service.domain.chat.domaiin.ChatMessage;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageDTO;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageReadAckRes;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageRes;
import site.danjam.mate.chat_service.domain.chat.repository.ChatMessageRepository;
import site.danjam.mate.chat_service.domain.chatRoom.exception.NotFoundChatRoomUserException;
import site.danjam.mate.chat_service.domain.chatRoom.repository.ChatRoomUserRepository;
import site.danjam.mate.chat_service.global.common.annotation.MethodDescription;
import site.danjam.mate.chat_service.global.infra.kafka.chat.producer.ChatProducer;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatQueryService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatProducer chatProducer;

    @MethodDescription(description = "읽지 않은 채팅 로그를 조회하고, 읽음 처리 한 후에 반환합니다.")
    public List<ChatMessageRes> getChatLogsAndMarkAsRead(Long chatRoomId, Long userId) {

        validateUserInChatRoom(chatRoomId, userId);
        markUnreadMessagesAsRead(chatRoomId, userId);

        return chatMessageRepository.findByChatRoomId(chatRoomId)
                .stream()
                .map(ChatMessageDTO::from)
                .map(ChatMessageRes::from)
                .collect(Collectors.toList());
    }

    @MethodDescription(description = "사용자가 채팅방 멤버인지 확인합니다.")
    private void validateUserInChatRoom(Long chatRoomId, Long userId) {
        Optional.ofNullable(chatRoomUserRepository.existsByChatRoomIdAndUserId(chatRoomId, userId))
                .filter(Boolean::booleanValue)
                .orElseThrow(NotFoundChatRoomUserException::new);
    }

    @MethodDescription(description = "읽지 않은 메시지를 읽음 처리하고 Ack 를 전송합니다.")
    private void markUnreadMessagesAsRead(Long chatRoomId, Long userId) {
        List<ChatMessage> unreadChats = chatMessageRepository.findByChatRoomIdAndUnreadUsersContains(chatRoomId,
                userId);

        if (unreadChats.isEmpty()) {
            log.info("No unread messages for userId: {} in chatRoomId: {}", userId, chatRoomId);
        }

        unreadChats.stream()
                .peek(chatMessage -> chatMessage.getUnreadUsers().remove(userId))
                .peek(chatMessage -> chatMessageRepository.save(chatMessage))
                .map(chatMessage -> {
                    int unreadCount = chatMessage.getUnreadUsers().size();
                    log.info("Marked message as read with messageId: {}, unread count: {} for user: {}",
                            chatMessage.getId(),
                            unreadCount, userId);
                    return new ChatMessageReadAckRes(chatMessage.getId(), unreadCount);
                }).forEach(
                        readAck -> { // 클라이언트에 전송
                            chatProducer.sendToChatAckTopic(readAck);
                            log.info("Read acknowledgement sent for messageId: {} ", readAck.getMessageId());
                        }
                );
    }
}
