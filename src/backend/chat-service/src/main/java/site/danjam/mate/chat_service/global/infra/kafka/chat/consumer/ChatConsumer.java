package site.danjam.mate.chat_service.global.infra.kafka.chat.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageDTO;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageRes;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatConsumer {

    private final SimpMessageSendingOperations messagingTemplate;

    @KafkaListener(topics = "${spring.kafka.topic.chat}", groupId = "${spring.kafka.consumer.group-id.chat}", containerFactory = "chatListenerContainerFactory")
    public void chatListener(ChatMessageDTO messageDTO) {
        Long chatRoomId = messageDTO.getChatRoomId();
        ChatMessageRes response = ChatMessageRes.from(messageDTO);

        messagingTemplate.convertAndSend("/queue/" + chatRoomId, response);
    }
}
