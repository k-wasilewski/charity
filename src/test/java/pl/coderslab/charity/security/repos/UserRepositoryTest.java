package pl.coderslab.charity.security.repos;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest extends CustomBeforeAll {
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void findByUsername() {
        assertNotNull(userRepository.findByUsername("test@test.pl"));
    }

    @Test
    @Transactional
    public void getPassword() {
        assertNotNull(userRepository.getPassword("test@test.pl"));
    }

    @Test
    @Transactional
    public void findByRoles_Id() {
        assertNotNull(userRepository.findByRoles_Id(1));
    }
}

