package site.danjam.mate.chat_service.domain.chatRoom.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoom;
import site.danjam.mate.chat_service.domain.chatRoom.domain.ChatRoomUser;
import site.danjam.mate.chat_service.domain.chatRoom.dto.ChatRoomLeaveDTO;
import site.danjam.mate.chat_service.domain.chatRoom.dto.GroupChatRoomCreateDTO;
import site.danjam.mate.chat_service.domain.chatRoom.dto.PersonalChatRoomCreateDTO;
import site.danjam.mate.chat_service.domain.chatRoom.dto.request.GroupChatRoomRequestDTO;
import site.danjam.mate.chat_service.domain.chatRoom.exception.InvalidRequestGenderException;
import site.danjam.mate.chat_service.domain.chatRoom.repository.ChatRoomRepository;
import site.danjam.mate.chat_service.domain.chatRoom.repository.ChatRoomUserRepository;
import site.danjam.mate.chat_service.global.client.UserServiceClient;
import site.danjam.mate.chat_service.global.client.dto.UserInfoDTO;
import site.danjam.mate.chat_service.global.common.annotation.MethodDescription;
import site.danjam.mate.chat_service.global.common.enums.MateType;
import site.danjam.mate.chat_service.global.common.enums.Role;

@Service
@Slf4j
@Transactional
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


    @MethodDescription(description = "그룹 채팅방을 생성합니다.")
    public GroupChatRoomCreateDTO createGroupChatRoom(Long userId, Role role, GroupChatRoomRequestDTO request) {

        List<Long> participants = request.getFriendUsernames().stream()
                .map(username -> userServiceClient.getUserInfo(username).getUserId())
                .collect(Collectors.toList());
        participants.add(userId);

        if (MateType.ROOMMATE.equals(request.getMateType())) {
            equalsGenderForGroup(userId, participants);
        }

        ChatRoom chatRoom = saveChatRoom(request.getTitle(), request.getMateType(), participants);
        return GroupChatRoomCreateDTO.from(chatRoom);
    }

    @MethodDescription(description = "채팅방을 데이터베이스에 저장합니다.")
    private ChatRoom saveChatRoom(String title, MateType mateType, List<Long> userIds) {
        ChatRoom chatRoom = ChatRoom.builder()
                .title(title)
                .mateType(mateType)
                .build();
        log.info("chatRoom = ", chatRoom);

        ChatRoom saveChatRoom = chatRoomRepository.save(chatRoom);

        List<ChatRoomUser> chatRoomUsers = new ArrayList<>();
        for (Long userId : userIds) {
            chatRoomUsers.add(ChatRoomUser.create(userId, saveChatRoom));
        }
        log.info("saveChatRoomUsers = ", chatRoomUsers);

        chatRoomUserRepository.saveAll(chatRoomUsers);

        return chatRoom;
    }


    // todo : 트랜잭션 분리하기 (현재  public 메서드의 Transactional 이 들어가있음 -> readOnly 로 변경)
    @MethodDescription(description = "룸 메이트 개인 채팅방 개설 시 서로의 성별이 같은 지 확인합니다.")
    private void equalsGender(Long userId, String participant) {
        UserInfoDTO userInfo = userServiceClient.getUserInfo(participant);
        UserInfoDTO friendInfo = userServiceClient.getUserInfo(userId);
        if (!userInfo.getGender().equals(friendInfo.getGender())) {
            throw new InvalidRequestGenderException();
        }
    }

    // todo : 트랜잭션 분리하기 (현재  public 메서드의 Transactional 이 들어가있음 -> readOnly 로 변경)
    @MethodDescription(description = "룸 메이트 그룹 채팅방 개설 시 서로의 성별이 같은 지 확인합니다.")
    private void equalsGenderForGroup(Long userId, List<Long> participants) {
        String gender = userServiceClient.getUserInfo(userId).getGender();
        List<UserInfoDTO> friendInfos = userServiceClient.getUserInfo(participants);

        List<String> invalidGenders = friendInfos.stream()
                .filter(user -> !user.getGender().equals(gender))
                .map(UserInfoDTO::getUsername)
                .collect(Collectors.toList());

        if (!invalidGenders.isEmpty()) {
            throw new InvalidRequestGenderException("성별이 같지 않은 유저가 존재합니다." + String.join(", ", invalidGenders));
        }
    }

    @MethodDescription(description = "사용자가 채팅방을 나갑니다. 채팅방에 유저가 존재하지 않을 경우 채팅방을 삭제합니다.")
    public ChatRoomLeaveDTO leaveChatRoom(Long userId, Long chatRoomId) {

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId);

        ChatRoomUser chatRoomUser = chatRoomUserRepository.findByChatRoomIdAndUserId(chatRoomId, userId);

        chatRoomUserRepository.delete(chatRoomUser);

        if (chatRoomUserRepository.countByChatRoomId(chatRoomId) == 0) {
            chatRoomRepository.deleteById(chatRoomId);
        }

        return ChatRoomLeaveDTO.from(chatRoom);
    }
}
