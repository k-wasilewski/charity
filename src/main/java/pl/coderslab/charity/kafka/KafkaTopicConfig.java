package pl.coderslab.charity.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
    private static String url;
    private String bootstrapAddress;

    public static void setUrl(String testUrl) {url=testUrl;}

    @Bean
    public KafkaAdmin kafkaAdmin() {
        if (url==null) bootstrapAddress = "kafka:9092";
        else bootstrapAddress = url;

        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic("user-to-inst", 1, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic("inst-to-user", 1, (short) 1);
    }
}