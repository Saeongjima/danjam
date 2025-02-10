package site.danjam.mate.chat_service.global.common.config.kafka.chat.consumer;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageDTO;

@EnableKafka
@Configuration
public class PersonalChatConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id.personal-chat}")
    private String groupId;

    @Bean
    public Map<String, Object> personalChatConsumerConfiguration() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.CLIENT_ID_CONFIG, groupId);
        return config;
    }

    @Bean
    public ConsumerFactory<String, ChatMessageDTO> personalChatConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                personalChatConsumerConfiguration(),
                new StringDeserializer(),
                new JsonDeserializer<>(ChatMessageDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ChatMessageDTO> directChatListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ChatMessageDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(personalChatConsumerFactory());
        return factory;
    }
}
