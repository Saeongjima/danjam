package site.danjam.mate.chat_service.global.infra.kafka.chat.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageDTO;

@Slf4j
@Component
@RequiredArgsConstructor
public class PersonalChatProducer {

    @Value("${spring.kafka.topic.personal-chat}")
    private String personalChatTopic;

    private final KafkaTemplate<String, ChatMessageDTO> personalChatEventKafkaTemplate;

    public void sendToPersonalChatEventTopic(ChatMessageDTO chatDto) {
        personalChatEventKafkaTemplate.send(personalChatTopic, chatDto);
    }
}
