package site.danjam.mate.chat_service.domain.chatRoom.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.danjam.mate.chat_service.domain.chatRoom.dto.PersonalChatRoomCreateDTO;
import site.danjam.mate.chat_service.global.common.annotation.MethodDescription;
import site.danjam.mate.chat_service.global.common.enums.Role;

@Service
@RequiredArgsConstructor
public class ChatRoomCommandService {

    @MethodDescription(description = "1대1 채팅방을 생성합니다. 채팅방 이름은 상대방의 (큰 타이틀) 닉네임, (작은 타이틀)년도/학번/전공으로 생성됩니다.")
    public PersonalChatRoomCreateDTO createPersonalChatRoom(Long userId, Role role, PersonalChatRoomCreateDTO dto) {
        return PersonalChatRoomCreateDTO.builder().build();
    }
}
