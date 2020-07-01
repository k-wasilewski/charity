package pl.coderslab.charity.kafka;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.extensions.CustomBeforeAll;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaConfigTest extends CustomBeforeAll {
    @Autowired
    ConsumerFactory consumerFactory;

    @Test
    public void consumerFactory() {
        String kafkaContainerUrl = kafkaContainer.getBootstrapServers();

        String kafkaAppUrl =
                (String) consumerFactory.getConfigurationProperties().get("bootstrap.servers");

        assertEquals(kafkaContainerUrl, kafkaAppUrl);
    }
}
