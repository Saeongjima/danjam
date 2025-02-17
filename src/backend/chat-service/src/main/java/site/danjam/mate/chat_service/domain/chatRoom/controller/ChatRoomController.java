package site.danjam.mate.chat_service.domain.chatRoom.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.danjam.mate.chat_service.domain.chatRoom.dto.ChatRoomLeaveDTO;
import site.danjam.mate.chat_service.domain.chatRoom.dto.ChatRoomListDTO;
import site.danjam.mate.chat_service.domain.chatRoom.dto.GroupChatRoomCreateDTO;
import site.danjam.mate.chat_service.domain.chatRoom.dto.PersonalChatRoomCreateDTO;
import site.danjam.mate.chat_service.domain.chatRoom.dto.request.GroupChatRoomRequestDTO;
import site.danjam.mate.chat_service.domain.chatRoom.service.ChatRoomCommandService;
import site.danjam.mate.chat_service.domain.chatRoom.service.ChatRoomQueryService;
import site.danjam.mate.chat_service.global.common.dto.ApiResponseData;
import site.danjam.mate.chat_service.global.common.enums.Role;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat-service/api/chat-room")
public class ChatRoomController {
    private final ChatRoomCommandService chatRoomCommandService;
    private final ChatRoomQueryService chatRoomQueryService;

    @PostMapping("/personal")
    public ResponseEntity<ApiResponseData<PersonalChatRoomCreateDTO>> createPersonalChatRoom(
            @RequestHeader("userId") String userId, @RequestHeader("role") Role role,
            @RequestBody PersonalChatRoomCreateDTO dto) {
        return ResponseEntity.ok(ApiResponseData.of(chatRoomCommandService.createPersonalChatRoom(userId, role, dto),
                "채팅방이 생성되었습니다."));
    }

    @PostMapping("/group")
    public ResponseEntity<ApiResponseData<GroupChatRoomCreateDTO>> createGroupChatRoom(
            @RequestHeader("userId") String userId, @RequestHeader("role") Role role,
            @RequestBody GroupChatRoomRequestDTO dto) {
        return ResponseEntity.ok(ApiResponseData.of(chatRoomCommandService.createGroupChatRoom(userId, role, dto),
                "채팅방이 생성되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiResponseData<List<ChatRoomListDTO>>> getChatRoomList(
            @RequestHeader("userId") String userId) {
        return ResponseEntity.ok(ApiResponseData.of(chatRoomQueryService.getUserChatRoom(userId), "채팅방 목록 조회 성공"));
    }

    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<ApiResponseData<ChatRoomLeaveDTO>> leaveChatRoom(@RequestHeader("userId") String userId,
                                                                           @PathVariable("chatRoomId") Long chatRoomId) {
        return ResponseEntity.ok(
                ApiResponseData.of(chatRoomCommandService.leaveChatRoom(userId, chatRoomId), "정상적으로 채팅방을 나갔습니다."));
    }
}
