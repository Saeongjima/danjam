package site.danjam.mate.chat_service.domain.chat.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.danjam.mate.chat_service.domain.chat.domaiin.ChatMessage;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageDTO;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageReadAckRes;
import site.danjam.mate.chat_service.domain.chat.dto.request.ChatMessageReq;
import site.danjam.mate.chat_service.domain.chat.repository.ChatMessageRepository;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.domain.chatRoom.exception.NotFoundChatRoomUserException;
import site.danjam.mate.chat_service.domain.chatRoom.repository.ChatRoomRepository;
import site.danjam.mate.chat_service.domain.chatRoom.repository.ChatRoomUserRepository;
import site.danjam.mate.chat_service.global.common.annotation.MethodDescription;
import site.danjam.mate.chat_service.global.infra.kafka.chat.producer.ChatProducer;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatCommandService {
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatProducer chatProducer;

    @MethodDescription(description = "메시지를 전송합니다.")
    public void sendMessage(Long chatRoomId, ChatMessageReq request, String user) {
        Long userId = Long.valueOf(user);

        // todo : MateType 프로필 공유를 위해 chatRoom 에서 mateType 뽑아오기
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);

        if (!isUserInChatRoom(chatRoomId, userId)) {
            throw new NotFoundChatRoomUserException();
        }

        // todo : 프로필 공유 메시지 확인

        List<Long> unreadUsers = chatRoomUserRepository.findAllUserIdsByChatRoomId(chatRoomId)
                .stream()
                .filter(u -> !u.equals(userId))
                .collect(Collectors.toList());

        ChatMessage chatMessage = createChatMessage(request, user, chatRoomId, unreadUsers);
        ChatMessageDTO chatMessageDto = ChatMessageDTO.from(chatMessage);

        chatProducer.sendToChatEventTopic(chatMessageDto);
    }


    @MethodDescription(description = "클라언트로부터 Ack 를 받아 메시지 읽음 처리를 합니다.")
    public void markMessageAsRead(Long chatRoomId, Long messageId, String user) {
        ChatMessage chatMessage = chatMessageRepository.findById(messageId);

        if (chatMessage.getUnreadUsers().contains(user)) {
            chatMessage.getUnreadUsers().remove(user);
            chatMessageRepository.save(chatMessage);
            int unreadCount = chatMessage.getUnreadUsers().size();

            ChatMessageReadAckRes ackRes = ChatMessageReadAckRes.builder().messageId(messageId).unreadCount(unreadCount)
                    .build();
            //
        } else {
            throw new NotFoundChatRoomUserException();
        }
    }

    @MethodDescription(description = "채팅 메시지 객체를 생성합니다.")
    private ChatMessage createChatMessage(ChatMessageReq request, String sender, Long chatRoomId,
                                          List<Long> unreadUsers) {
        return chatMessageRepository.save(ChatMessage.builder()
                .message(request.getMessage())
                .sender(sender)
                .chatRoomId(chatRoomId)
                .chatType(request.getChatType())
                .unreadUsers(unreadUsers)
                .build());
    }

    @MethodDescription(description = "채팅방에 유저가 존재하는 지 확인합니다.")
    private boolean isUserInChatRoom(Long chatRoomId, Long userId) {
        return chatRoomUserRepository.existsByChatRoomIdAndUserId(chatRoomId, userId);
    }
}
