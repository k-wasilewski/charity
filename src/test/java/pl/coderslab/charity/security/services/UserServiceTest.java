package pl.coderslab.charity.security.services;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.KafkaContainer;
import pl.coderslab.charity.entities.VerificationToken;
import pl.coderslab.charity.kafka.KafkaConsumerConfig;
import pl.coderslab.charity.kafka.KafkaProducerConfig;
import pl.coderslab.charity.kafka.KafkaTopicConfig;
import pl.coderslab.charity.repos.VerificationTokenRepository;
import pl.coderslab.charity.security.entities.Role;
import pl.coderslab.charity.security.entities.User;
import pl.coderslab.charity.security.repos.RoleRepository;
import pl.coderslab.charity.security.repos.UserRepository;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    VerificationTokenRepository tokenRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @ClassRule
    public static KafkaContainer kafkaContainer = new KafkaContainer();

    @BeforeClass
    public static void setKafkaContainerName() {
        kafkaContainer.setNetworkAliases(Arrays.asList("kafka"));
        KafkaConsumerConfig.setUrl(kafkaContainer.getBootstrapServers());
        KafkaProducerConfig.setUrl(kafkaContainer.getBootstrapServers());
        KafkaTopicConfig.setUrl(kafkaContainer.getBootstrapServers());
    }

    @Test
    @Transactional
    public void findByUserName() {
        assertNotNull(userService.findByUserName("test@test.pl"));
    }

    @Test
    @Transactional
    public void saveUser() {
        final User testUser = new User();
        final String password = "abc";
        final String username = "test user";
        final Role userRole = roleRepository.findByName("ROLE_USER");
        testUser.setUsername(username);
        testUser.setPassword(password);

        userService.saveUser(testUser);

        User savedUser = userRepository.findByUsername(username);
        assertNotNull(savedUser);
        assertNotEquals(password, savedUser.getPassword());
        assertEquals(1, savedUser.getBlocked());
        assertTrue(savedUser.getRoles().contains(userRole));
    }

    @Test
    @Transactional
    public void activateUser() {
        userService.activateUser(userRepository.findByUsername("test@test.pl"));

        assertEquals(0, userRepository.findByUsername("test@test.pl").getBlocked());
        assertEquals(1, userRepository.findByUsername("test@test.pl").getEnabled());
    }

    @Test
    @Transactional
    public void saveAdmin() {
        final User testAdmin = new User();
        final String password = "abc";
        final String username = "test admin";
        final Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        testAdmin.setUsername(username);
        testAdmin.setPassword(password);

        userService.saveAdmin(testAdmin);

        User savedAdmin = userRepository.findByUsername(username);
        assertNotNull(savedAdmin);
        assertNotEquals(password, savedAdmin.getPassword());
        assertEquals(1, savedAdmin.getEnabled());
        assertTrue(savedAdmin.getRoles().contains(adminRole));
    }

    @Test
    @Transactional
    public void saveInstitution() {
        final User testInstitution = new User();
        final String password = "abc";
        final String username = "test instit";
        final Role institRole = roleRepository.findByName("ROLE_INSTITUTION");
        testInstitution.setUsername(username);
        testInstitution.setPassword(password);

        userService.saveInstitution(testInstitution);

        User savedInstitution = userRepository.findByUsername(username);
        assertNotNull(savedInstitution);
        assertNotEquals(password, savedInstitution.getPassword());
        assertEquals(1, savedInstitution.getEnabled());
        assertTrue(savedInstitution.getRoles().contains(institRole));
    }

    @Test
    @Transactional
    public void changePwd() {
        User user = userRepository.findByUsername("test@test.pl");
        String oldPwd = user.getPassword();

        userService.changePwd(user, "changedPassword");

        assertNotEquals(oldPwd, userRepository.findByUsername("test@test.pl").getPassword());
    }

    @Test
    @Transactional
    public void confirmPwd() {
        String username = "test@test.pl";

        assertTrue(userService.confirmPwd(username, "test"));
        assertFalse(userService.confirmPwd(username, "fdgfgdg"));
    }

    @Test
    @Transactional
    public void getVerificationToken() throws Exception {
        final String username = "test@name.pl";

        mockMvc.perform(post("/register?username="+username+"&" +
                "password=Abc$54gdd&password2=Abc$54gdd"))
                .andExpect(status().isOk())
                .andReturn();

        User user = userRepository.findByUsername(username);
        VerificationToken verificationToken = tokenRepository.findByUser(user);
        String token = verificationToken.getToken();

        assertEquals(verificationToken, userService.getVerificationToken(token));
    }

    @Test
    @Transactional
    public void createVerificationToken() {
        final User user = userRepository.findByUsername("test@test.pl");
        final String token = "ergetgergerg";

        userService.createVerificationToken(user, token);

        VerificationToken verificationToken = tokenRepository.findByToken(token);
        assertNotNull(verificationToken);
        assertEquals(user, verificationToken.getUser());
    }
}

