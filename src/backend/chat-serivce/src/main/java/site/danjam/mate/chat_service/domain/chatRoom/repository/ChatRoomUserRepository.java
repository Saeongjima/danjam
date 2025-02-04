package site.danjam.mate.chat_service.domain.chatRoom.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chatRoom.repository.jpa.ChatRoomUserJpaRepository;

@Repository
@RequiredArgsConstructor
public class ChatRoomUserRepository {
    private ChatRoomUserJpaRepository chatRoomUserJpaRepository;
}
