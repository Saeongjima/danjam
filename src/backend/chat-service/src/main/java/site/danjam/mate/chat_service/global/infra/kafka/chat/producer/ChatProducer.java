package site.danjam.mate.chat_service.global.infra.kafka.chat.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageDTO;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageReadAckRes;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatProducer {

    @Value("${spring.kafka.topic.chat}")
    private String chatTopic;
    @Value("${spring.kafka.topic.chat}")
    private String ackTopic;

    private final KafkaTemplate<String, ChatMessageDTO> chatEventKafkaTemplate;
    private final KafkaTemplate<String, ChatMessageReadAckRes> ackKafkaTemplate;


    public void sendToChatEventTopic(ChatMessageDTO chatDto) {
        chatEventKafkaTemplate.send(chatTopic, chatDto);
    }

    public void sendToChatAckTopic(ChatMessageReadAckRes ackRes) {
        ackKafkaTemplate.send(ackTopic, ackRes);
    }
}
