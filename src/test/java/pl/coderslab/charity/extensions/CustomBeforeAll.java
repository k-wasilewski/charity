package pl.coderslab.charity.extensions;

import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;

import java.util.Arrays;

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
