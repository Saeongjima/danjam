package site.danjam.mate.chat_service.domain.chatRoom.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoomUser;
import site.danjam.mate.chat_service.domain.chatRoom.dto.PersonalChatRoomCreateDTO;
import site.danjam.mate.chat_service.domain.chatRoom.exception.InvalidRequestException;
import site.danjam.mate.chat_service.domain.chatRoom.repository.ChatRoomRepository;
import site.danjam.mate.chat_service.domain.chatRoom.repository.ChatRoomUserRepository;
import site.danjam.mate.chat_service.global.client.UserServiceClient;
import site.danjam.mate.chat_service.global.client.dto.UserGenderDTO;
import site.danjam.mate.chat_service.global.common.annotation.MethodDescription;
import site.danjam.mate.chat_service.global.common.enums.MateType;
import site.danjam.mate.chat_service.global.common.enums.Role;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomCommandService {
    private final UserServiceClient userServiceClient;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    @MethodDescription(description = "1대1 채팅방을 생성합니다. 채팅방 이름은 상대방의 (큰 타이틀) 닉네임, (작은 타이틀)년도/학번/전공으로 생성됩니다.")
    public PersonalChatRoomCreateDTO createPersonalChatRoom(Long userId, Role role, PersonalChatRoomCreateDTO dto) {
        if (MateType.ROOMMATE.equals(dto.getMateType())) {
            equalsGender(userId, dto.getFriendUsername());
        }

        ChatRoom existChatRoom = chatRoomRepository
                .findExistingPersonalChatRoom(userId, dto.getFriendId())
                .orElseGet(() -> {
                    return saveChatRoom(dto.getTitle(), dto.getMateType(), Arrays.asList(userId, dto.getFriendId()));
                });
        return PersonalChatRoomCreateDTO.of(existChatRoom, dto.getFriendUsername());
    }

    @MethodDescription(description = "룸 메이트 채팅방 개설 시 서로의 성별이 같은 지 확인합니다.")
    private void equalsGender(Long userId, String friendName) {
        UserGenderDTO dto = userServiceClient.getUserGender(userId, friendName);
        if (!dto.getGender().equals(dto.getFriendGender())) {
            throw new InvalidRequestException();
        }
    }

    @MethodDescription(description = "채팅방을 데이터베이스에 저장합니다.")
    private ChatRoom saveChatRoom(String title, MateType mateType, List<Long> user) {
        ChatRoom chatRoom = ChatRoom.builder()
                .title(title)
                .mateType(mateType)
                .build();
        log.info("chatRoom = ", chatRoom);

        ChatRoom saveChatRoom = chatRoomRepository.save(chatRoom);

        List<ChatRoomUser> chatRoomUsers = new ArrayList<>();
        for (Long userId : user) {
            chatRoomUsers.add(ChatRoomUser.create(userId, saveChatRoom));
        }
        log.info("saveChatRoomUsers = ", chatRoomUsers);

        chatRoomUserRepository.saveAll(chatRoomUsers);

        return chatRoom;
    }
}
