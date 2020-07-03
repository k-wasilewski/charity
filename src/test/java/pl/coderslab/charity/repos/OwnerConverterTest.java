package pl.coderslab.charity.repos;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OwnerConverterTest extends CustomBeforeAll {
    @Autowired
    UserRepository userRepository;
    @Autowired
    OwnerConverter ownerConverter;
    final String USERNAME = "test@user.com";
    User user;

    @Before
    public void setup() {
        user = new User();
        user.setUsername(USERNAME);
        userRepository.save(user);
    }

    @After
    public void restore() {
        userRepository.delete(userRepository.findByUsername(USERNAME));
    }

    @Test
    public void convert() {
        User convertedUser = ownerConverter.convert(USERNAME);

        assertEquals(user.getUsername(), convertedUser.getUsername());
    }
}
