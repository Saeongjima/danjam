package site.danjam.mate.chat_service.domain.chatRoom.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.chat_service.domain.chat.domaiin.ChatMessage;
import site.danjam.mate.chat_service.domain.chat.repository.ChatMessageRepository;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.domain.chatRoom.dto.ChatRoomListDTO;
import site.danjam.mate.chat_service.domain.chatRoom.repository.ChatRoomUserRepository;
import site.danjam.mate.chat_service.global.common.annotation.MethodDescription;

@Service
@RequiredArgsConstructor
public class ChatRoomQueryService {
    private final ChatRoomUserRepository chatRoomUserRepository;
    private final ChatMessageRepository chatMessageRepository;

    @MethodDescription(description = "채팅방 리스트를 가져옵니다. 각 채팅방은 마지막으로 수신한 메시지를 가져옵니다.")
    public List<ChatRoomListDTO> getUserChatRoom(String user) {
        Long userId = Long.valueOf(user);
        List<ChatRoom> chatRooms = chatRoomUserRepository.findChatRoomByUserId(userId);
        Map<ChatRoom, ChatMessage> lastMessage = new HashMap<>();

        for (ChatRoom chatRoom : chatRooms) {
            chatMessageRepository.findLastMessageByChatRoomId(chatRoom.getId())
                    .ifPresent(message -> lastMessage.put(chatRoom, message));
        }

        return chatRooms.stream()
                .map(chatRoom -> {
                    Integer unreadCount = chatMessageRepository.countUnreadMessages(chatRoom.getId(), userId);

                    return ChatRoomListDTO.of(chatRoom, lastMessage.get(chatRoom), unreadCount);
                }).collect(Collectors.toList());
    }
}
