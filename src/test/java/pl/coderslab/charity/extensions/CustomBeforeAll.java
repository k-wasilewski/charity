package pl.coderslab.charity.extensions;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;

import java.util.Arrays;

import static org.junit.jupiter.api.extension.ExtensionContext.Namespace.GLOBAL;

public class CustomBeforeAll {
    private static boolean started = false;
    public static KafkaContainer kafkaContainer = new KafkaContainer();

    static{
        if (!started) {
            started = true;

            kafkaContainer.start();

            kafkaContainer.setNetworkAliases(Arrays.asList("kafka"));
            KafkaConsumerConfig.setUrl(kafkaContainer.getBootstrapServers());
            KafkaProducerConfig.setUrl(kafkaContainer.getBootstrapServers());
            KafkaTopicConfig.setUrl(kafkaContainer.getBootstrapServers());
        }
    }
}
