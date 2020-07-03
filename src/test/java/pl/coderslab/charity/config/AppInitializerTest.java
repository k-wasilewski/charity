package pl.coderslab.charity.config;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.extensions.CustomBeforeAll;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.repos.InstitutionRepository;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.UserRepository;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppInitializerTest extends CustomBeforeAll {
    @Autowired
    InstitutionRepository institutionRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void init() {
        User admin1 = userRepository.findByUsername("admin1@admin.pl");
        assertNotNull(admin1);
        assertEquals(1, admin1.getEnabled());
        assertEquals(0, admin1.getBlocked());

        User admin2 = userRepository.findByUsername("admin2@admin.pl");
        assertNotNull(admin2);
        assertEquals(1, admin2.getEnabled());
        assertEquals(0, admin2.getBlocked());

        User instit = userRepository.findByUsername("dbamozdrowie@wp.pl");
        assertNotNull(instit);
        assertEquals(1, instit.getEnabled());
        assertEquals(0, instit.getBlocked());
        assertNotNull(instit.getInstitution());

        User testUser = userRepository.findByUsername("test@test.pl");
        assertNotNull(testUser);
        assertEquals(1, testUser.getEnabled());
        assertEquals(0, testUser.getBlocked());
    }

}