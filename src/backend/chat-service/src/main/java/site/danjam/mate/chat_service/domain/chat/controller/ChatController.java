package site.danjam.mate.chat_service.domain.chat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageRes;
import site.danjam.mate.chat_service.domain.chat.dto.request.ChatMessageReq;
import site.danjam.mate.chat_service.domain.chat.service.ChatCommandService;
import site.danjam.mate.chat_service.domain.chat.service.ChatQueryService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ChatController {
    private final ChatCommandService chatCommandService;
    private final ChatQueryService chatQueryService;

    @MessageMapping("chat-rooms.{chatRoomId}")
    public void sendMessage(@DestinationVariable Long chatRoomId, @Payload ChatMessageReq chatMessageReq,
                            @RequestHeader("userId") Long userId) {
        log.info("[+] [ChatController SendMessage] Called ChatRoomId: {}, UserId: {}", chatRoomId, userId);
        chatCommandService.sendMessage(chatRoomId, chatMessageReq, userId);
    }

    @MessageMapping("chat-rooms.{chatRoomId}.logs")
    @SendToUser("/queue/chat-rooms.logs")
    public List<ChatMessageRes> loadChatLogsAndMarkAsRead(@DestinationVariable Long chatRoomId,
                                                          @RequestHeader("userId") Long userId) {
        log.info("[+] [ChatController loadChatLogsAndMarkAsRead] Called ChatRoomId: {}, UserId: {}", chatRoomId,
                userId);
        return chatQueryService.getChatLogsAndMarkAsRead(chatRoomId, userId);
    }

    @MessageMapping("chat-rooms.{chatRoomId}.read")
    public void markAsRead(@DestinationVariable Long chatRoomId, @Payload Long messageId,
                           @RequestHeader("userId") Long userId) {
        log.info("[+] [ChatController markAsRead] Called ChatRoomId: {}, MessageId: {}, UserId: {} ", chatRoomId,
                messageId, userId);
        chatCommandService.markMessageAsRead(chatRoomId, messageId, userId);
    }
}
