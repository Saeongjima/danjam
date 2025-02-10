package site.danjam.mate.chat_service.global.common.config.kafka.chat.producer;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import site.danjam.mate.chat_service.domain.chat.dto.ChatMessageDTO;

@EnableKafka
@Configuration
public class PersonalChatProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> chatEventProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return config;
    }

    @Bean
    public ProducerFactory<String, ChatMessageDTO> personalChatProducerFactory() {
        return new DefaultKafkaProducerFactory<>(chatEventProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, ChatMessageDTO> serverChatKafkaTemplate() {
        return new KafkaTemplate<>(personalChatProducerFactory());
    }
}
