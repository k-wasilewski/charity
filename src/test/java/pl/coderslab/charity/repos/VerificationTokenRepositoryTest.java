package pl.coderslab.charity.repos;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VerificationTokenRepositoryTest extends CustomBeforeAll {
    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void findByToken() {
        User user = new User();
        user.setUsername("test user1");
        userRepository.save(user);
        VerificationToken verificationToken = new VerificationToken();
        String token = "test token1";
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationTokenRepository.save(verificationToken);

        assertEquals(verificationToken, verificationTokenRepository.findByToken(token));
    }

    @Test
    @Transactional
    public void findByUser() {
        User user = new User();
        user.setUsername("test user2");
        userRepository.save(user);
        VerificationToken verificationToken = new VerificationToken();
        String token = "test token2";
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationTokenRepository.save(verificationToken);

        assertEquals(verificationToken, verificationTokenRepository.findByUser(user));
    }
}

