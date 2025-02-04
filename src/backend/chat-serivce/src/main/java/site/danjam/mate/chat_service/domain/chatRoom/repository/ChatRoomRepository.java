package site.danjam.mate.chat_service.domain.chatRoom.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import site.danjam.mate.chat_service.domain.chatRoom.repository.jpa.ChatRoomJpaRepository;

@Repository
@RequiredArgsConstructor
public class ChatRoomRepository {
    private final ChatRoomJpaRepository chatRoomJpaRepository;

}
