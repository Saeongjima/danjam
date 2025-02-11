package site.danjam.mate.chat_service.domain.chatRoom.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.chat_service.domain.chatRoom.dto.PersonalChatRoomCreateDTO;
import site.danjam.mate.chat_service.domain.chatRoom.service.ChatRoomCommandService;
import site.danjam.mate.chat_service.global.common.dto.ApiResponseData;
import site.danjam.mate.chat_service.global.common.enums.Role;

@RestController
@RequiredArgsConstructor
@RequestMapping("chat-service/api/chat-room")
public class ChatRoomController {

    private final ChatRoomCommandService chatRoomCommandService;


    @PostMapping
    public ResponseEntity<ApiResponseData<PersonalChatRoomCreateDTO>> createPersonalChatRoom(
            @RequestHeader("userId") Long userId, @RequestHeader("role") Role role,
            @RequestBody PersonalChatRoomCreateDTO dto) {
        return ResponseEntity.ok(ApiResponseData.of(chatRoomCommandService.createPersonalChatRoom(userId, role, dto),
                "채팅방이 생성되었습니다."));
    }
}
